package com.example.eliteedu_prism.EliteEdu_Prism.service.impl;

import com.example.eliteedu_prism.EliteEdu_Prism.mapper.DiscussionsMapper;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Comments;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Posts;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Topics;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.User;
import com.example.eliteedu_prism.EliteEdu_Prism.service.DiscussionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DiscussionsServiceImpl implements DiscussionsService {



    @Autowired
    DiscussionsMapper discussionsMapper;

    @Override
    public void submitDiscussion(String title, String content, String userId, int courseId, String topic, String url) {
        discussionsMapper.submitDiscussion(title,content, Integer.parseInt(userId), courseId, topic, url);
    }

    @Override
    public void submitDiscussion1(String title, String content, String userId, int courseId, String url) {
        discussionsMapper.submitDiscussion1(title,content, Integer.parseInt(userId), courseId, url);
    }

    @Override
    public void submitDiscussion2(String title, String content, String userId, int courseId, String topic) {
        discussionsMapper.submitDiscussion2(title,content, Integer.parseInt(userId), courseId, topic);
    }

    @Override
    public void submitDiscussion3(String title, String content, String userId, int courseId) {
        discussionsMapper.submitDiscussion3(title,content, Integer.parseInt(userId), courseId);
    }

    @Override
    public List<Posts> getDiscussion(int courseId) {
        return discussionsMapper.getDiscussion(courseId);
    }

    @Override
    public List<Topics> getTopics() {
        return discussionsMapper.getTopics();
    }

    @Override
    public void createTopic(String topic) {
        discussionsMapper.createTopic(topic);
    }

    @Override
    public void toggleLike(int postId) {
        discussionsMapper.toggleLike(postId);

    }

    @Override
    public void deletePost(int postId) {
        discussionsMapper.deletePost(postId);
    }

    @Override
    public Comments fetch(int postId) {
        return null;
    }


    @Override
    public Posts fetchPost(int postId) {
        return discussionsMapper.fetchPost(postId);
    }

    @Override
    public List<Comments> fetchComments(int postId) {
        return discussionsMapper.fetch(postId);
    }

    @Override
    public Comments getUserBypId(int id) {
        return discussionsMapper.getUserBypId(id);
    }

    @Override
    public void submitCommentAPI(int postId, String comment, String userId) {

        discussionsMapper.submitCommentAPI(postId, comment, Integer.parseInt(userId));

    }

    @Override
    public void likeComment(int id, String userId) {
        discussionsMapper.likeComment(id, Integer.parseInt(userId));
    }

    @Override
    public void submitReply(int id, String reply, int postId, String userId) {
        discussionsMapper.submitReply(id, reply, postId, Integer.parseInt(userId));
    }

    @Override
    public void deleteComment(int id) {
        discussionsMapper.deleteComment(id);
    }

    @Override
    public List<User> fetchuser(String userId, int courseId) {
        return discussionsMapper.fetchuser(Integer.parseInt(userId), courseId);
    }

    @Override
    public List<User> fetchuser2(String content) {

        //@aaa @333 @777
        //nihao@
        List<User> users = new ArrayList<>();

        // 使用正则表达式匹配所有以 @ 开头的用户名
        Pattern pattern = Pattern.compile("@(\\S+)");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String username = matcher.group(0); // 获取完整的 @ 用户名
            User user = new User();
            user.setUsername(username.substring(1));// 去掉 @ 符号

            User u = discussionsMapper.getid(user.getUsername());

            if (u == null) {
                user.setUserId(0);
            }
            if (u != null) {
                user.setUserId(u.getUserId());
            }

            users.add(user); // 封装到 User 对象并添加到列表中
        }
        System.out.println(users);


        return users; // 返回包含所有用户的列表


    }

    @Override
    public User getpostUserBypId(int postId) {
        return discussionsMapper.getpostUserBypId(postId);
    }

    @Override
    public int getCourseId(int postId) {
        return discussionsMapper.getCourseId(postId);
    }

    @Override
    public int getCommentsUserid(int id) {
        return discussionsMapper.getCommentsUserid(id);
    }

    @Override
    public Comments fetchCommentsById(int id) {
        return discussionsMapper.fetchCommentsById(id);
    }


}
