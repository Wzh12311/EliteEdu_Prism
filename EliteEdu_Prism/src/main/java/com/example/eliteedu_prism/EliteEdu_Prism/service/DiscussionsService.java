package com.example.eliteedu_prism.EliteEdu_Prism.service;

import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Comments;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Posts;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Topics;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.User;

import java.util.List;

public interface DiscussionsService {
    void submitDiscussion(String title,String content, String userId, int courseId, String topic, String url);


    void submitDiscussion1(String title,String content, String userId, int courseId, String url);

    void submitDiscussion2(String title, String content, String userId, int courseId, String topic);

    void submitDiscussion3(String title,String content, String userId, int courseId);

    List<Posts> getDiscussion(int courseId);

    List<Topics> getTopics();

    void createTopic(String topic);

    void toggleLike(int postId);

    void deletePost(int postId);

    Comments fetch(int postId);

    Posts fetchPost(int postId);

    List<Comments> fetchComments(int postId);

    Comments getUserBypId(int id);

    void submitCommentAPI(int postId, String comment, String userId);

    void likeComment(int id, String userId);

    void submitReply(int id, String reply, int postId, String userId);

    void deleteComment(int id);

    List<User> fetchuser(String userId, int courseId);

    List<User> fetchuser2(String content);

    User getpostUserBypId(int postId);

    int getCourseId(int postId);

    int getCommentsUserid(int id);


    Comments fetchCommentsById(int id);
}
