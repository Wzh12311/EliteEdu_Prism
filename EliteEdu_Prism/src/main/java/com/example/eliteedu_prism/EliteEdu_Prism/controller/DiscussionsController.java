package com.example.eliteedu_prism.EliteEdu_Prism.controller;


import com.example.eliteedu_prism.EliteEdu_Prism.pojo.*;
import com.example.eliteedu_prism.EliteEdu_Prism.service.AssignmentService;
import com.example.eliteedu_prism.EliteEdu_Prism.service.DiscussionsService;
import com.example.eliteedu_prism.EliteEdu_Prism.service.UserService;
import com.example.eliteedu_prism.EliteEdu_Prism.utils.AliOSSUtils;
import com.example.eliteedu_prism.EliteEdu_Prism.utils.GetTokenUserId;
import com.example.eliteedu_prism.EliteEdu_Prism.utils.processComments;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


@CrossOrigin
@Slf4j
@RestController
public class DiscussionsController {

    @Autowired
    private DiscussionsService discussionsService;
    @Autowired
    private UserService userService;
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    AliOSSUtils aliOSSUtils;

    @PostMapping("submitDiscussion")
    public Result submitDiscussion(@RequestParam("content") String content,
                                   @RequestParam("title") String title,
                                   @RequestParam("courseId") int courseId,
                                   @RequestParam(value = "topic", required = false) String topic,
                                   @RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) throws IOException {

        String userId = GetTokenUserId.getUserId(request);

        if (file != null && !file.isEmpty() && topic != null && !topic.isEmpty()) {
            String url = aliOSSUtils.upload(file);
            discussionsService.submitDiscussion(title,content, userId, courseId, topic, url);

        } else if (topic == null && file != null && !file.isEmpty()) {
            String url = aliOSSUtils.upload(file);
            discussionsService.submitDiscussion1(title,content, userId, courseId, url);

        } else if (file == null && topic != null && !topic.isEmpty()) {

            discussionsService.submitDiscussion2(title,content, userId, courseId, topic);
        } else {

            discussionsService.submitDiscussion3(title,content, userId, courseId);
        }
        return Result.success();


    }

    @GetMapping("/getDiscussions")
    public Result getDiscussion(@RequestParam("courseId") int courseId) {
        List<Posts> list = discussionsService.getDiscussion(courseId);

//        System.out.println(list);
        return Result.success(list);
    }

    @GetMapping("getTopics")
    public Result getTopics() {
        List<Topics> list = discussionsService.getTopics();
        return Result.success(list);

    }

    @PostMapping("/createTopic")
    public Result createTopic(@RequestParam("newTopic") String newTopic) {
        String topic = "#" + newTopic;
//        System.out.println(topic);
        discussionsService.createTopic(topic);
        return Result.success();
    }

    @PostMapping("/toggleLike")
    public Result toggleLike(@RequestParam("id") int postId, HttpServletRequest request) {

        String userId = GetTokenUserId.getUserId(request);
        discussionsService.toggleLike(postId);
        //TODO 发送通知功能，通过userId，如果是自己给自己点赞，不需要发送通知
        // .....
        User user = discussionsService.getpostUserBypId(postId);
        User userProfile = userService.getUserProfile(userId);
        if( userProfile.getUserId()!=user.getUserId()){
            Posts post = discussionsService.fetchPost(postId);
            Notifications notifications =new Notifications();
            notifications.setType("interaction");
            notifications.setTitle("点赞");
            notifications.setSenderId(userProfile.getUserId());
            notifications.setContent(userProfile.getUsername()+"点赞了你的帖子:  "+post.getTitle());
            notifications.setPostId(postId);
            int courseId = discussionsService.getCourseId(postId);
            notifications.setCourseId(courseId);
            assignmentService.Notice(notifications);
            assignmentService.sendNotice(notifications.getId(),user.getUserId());
        }

        return Result.success();

    }

    @DeleteMapping("/deletePost")
    public Result deletePost(@RequestParam("id") int postId, HttpServletRequest request) {
        String userId = GetTokenUserId.getUserId(request);

        //TODO 根据 userId发送通知
        // ......
//        System.out.println(userId);

        User user = discussionsService.getpostUserBypId(postId);
        Notifications notifications =new Notifications();
        User userProfile = userService.getUserProfile(userId);
        if( userProfile.getUserId()!=user.getUserId()){
            Posts post = discussionsService.fetchPost(postId);

            notifications.setType("interaction");
            notifications.setTitle("删除帖子");
            notifications.setSenderId(userProfile.getUserId());
            notifications.setContent(userProfile.getUsername()+"删除了你的帖子:  "+post.getTitle());
            notifications.setPostId(0);
            notifications.setCourseId(0);

        }

        discussionsService.deletePost(postId);
        assignmentService.Notice(notifications);
        assignmentService.sendNotice(notifications.getId(),user.getUserId());




        return Result.success();
    }

    @GetMapping("/fetchComments")
    public Result fetchComments(@RequestParam("id") int postId) {
        List<Comments> comments = discussionsService.fetchComments(postId);

        List<Comments> comments1 = processComments.processComments(comments);

        return Result.success(comments1);


    }

    @GetMapping("/fetchPost")
    public Result fetchPost(@RequestParam("id") int postId) {
        Posts post = discussionsService.fetchPost(postId);
        return Result.success(post);

    }

    @GetMapping("getUserBypId")
    public Result getUserBypId(@RequestParam("id") int id){

        return Result.success(discussionsService.getUserBypId(id));
    }

    @PostMapping("submitCommentAPI")
    public Result submitCommentAPI(@RequestParam("postId") int postId, @RequestParam("content") String comment, HttpServletRequest request) {

        if(Objects.equals(comment, ""))
        {
            return Result.error("Comment cannot be empty");
        }
        String userId = GetTokenUserId.getUserId(request);
        discussionsService.submitCommentAPI(postId, comment, userId);
        User userProfile = userService.getUserProfile(userId);
        Notifications notifications =new Notifications();
        notifications.setType("interaction");
        notifications.setTitle("评论");
        notifications.setSenderId(userProfile.getUserId());
        notifications.setContent(userProfile.getUsername()+"评论了你的帖子:  "+comment);
        notifications.setPostId(postId);
        int courseId = discussionsService.getCourseId(postId);
        notifications.setCourseId(courseId);
        assignmentService.Notice(notifications);
        User user = discussionsService.getpostUserBypId(postId);
        assignmentService.sendNotice(notifications.getId(),user.getUserId());

        return Result.success();
    }
    @PostMapping("likeComment")
    public Result likeComment(@RequestParam("id") int id, HttpServletRequest request) {
        String userId = GetTokenUserId.getUserId(request);
        discussionsService.likeComment(id, userId);



        int userid = discussionsService.getCommentsUserid(id);
        User userProfile = userService.getUserProfile(userId);
        if( userProfile.getUserId()!=userid){


            Comments comments =discussionsService.fetchCommentsById(id);


            Notifications notifications =new Notifications();
            notifications.setType("interaction");
            notifications.setTitle("点赞");
            notifications.setSenderId(userProfile.getUserId());
            notifications.setContent(userProfile.getUsername()+"点赞了你的评论:  "+comments.getContent());
            notifications.setPostId(comments.getPostId());
            int courseId = discussionsService.getCourseId(comments.getPostId());
            notifications.setCourseId(courseId);
            assignmentService.Notice(notifications);
            assignmentService.sendNotice(notifications.getId(),userid);
        }


        return Result.success();
    }

    @PostMapping("submitReply")
    public Result submitReply(@RequestParam("id") int id, @RequestParam("content") String reply,@RequestParam("postId") int postId, HttpServletRequest request) {
        String userId = GetTokenUserId.getUserId(request);
        discussionsService.submitReply(id, reply,postId, userId);
        User userProfile = userService.getUserProfile(userId);

        Notifications notifications =new Notifications();
        notifications.setType("interaction");
        notifications.setTitle("评论");
        notifications.setSenderId(userProfile.getUserId());
        notifications.setContent(userProfile.getUsername()+"回复了你的帖子:  "+reply);
        notifications.setPostId(postId);
        int courseId = discussionsService.getCourseId(postId);
        notifications.setCourseId(courseId);
        assignmentService.Notice(notifications);
        User user = discussionsService.getpostUserBypId(postId);
        int userId1= discussionsService.getCommentsUserid(id);
        assignmentService.sendNotice(notifications.getId(),user.getUserId());
        if(userId1!=user.getUserId())
        {
            Notifications notifications1 =new Notifications();
            notifications1.setType("interaction");
            notifications1.setTitle("评论");
            notifications1.setSenderId(userProfile.getUserId());
            notifications1.setContent(userProfile.getUsername()+"回复了你的评论:  "+reply);
            notifications1.setPostId(postId);
            int courseId1 = discussionsService.getCourseId(postId);
            notifications1.setCourseId(courseId1);
            assignmentService.Notice(notifications1);
            assignmentService.sendNotice(notifications1.getId(),userId1);

        }


        return Result.success();
    }

    @DeleteMapping("deleteComment")
    public Result deleteComment(@RequestParam("id") int id, HttpServletRequest request) {

        String userId = GetTokenUserId.getUserId(request);
        //Todo 完成发送通知功能，通过token获取用户id
        User userProfile = userService.getUserProfile(userId);

        Comments comments= assignmentService.getcomment(id);
        //不能先删除!!
        discussionsService.deleteComment(id);

        if(comments.getUserId()!=userProfile.getUserId()){
            Notifications notifications =new Notifications();
            notifications.setType("interaction");
            notifications.setTitle("删除评论");
            notifications.setSenderId(userProfile.getUserId());
            notifications.setContent(userProfile.getUsername()+"删除了你的评论:  "+comments.getContent());
            notifications.setPostId(0);
            notifications.setCourseId(0);
            assignmentService.Notice(notifications);
            assignmentService.sendNotice(notifications.getId(),comments.getUserId());
        }

        return Result.success();


    }

    @GetMapping("fetch")
    public Result fetch(HttpServletRequest request,@RequestParam("courseId")int courseId) {
        String userId = GetTokenUserId.getUserId(request);

        List<User> user =discussionsService.fetchuser(userId,courseId);

        return Result.success(user);

    }

    @PostMapping("fetchuser2")
    public Result fetchuser2(@RequestParam("content") String content) {
        List<User> user = discussionsService.fetchuser2(content);
        return Result.success(user);
    }


}
