package com.example.eliteedu_prism.EliteEdu_Prism.mapper;

import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Comments;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Posts;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Topics;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DiscussionsMapper {

    @Insert("insert into posts (title,content, user_id, course_id, topic, image_url) values (#{title},#{content}, #{userId}, #{courseId}, #{topic}, #{url})")
    void submitDiscussion(String title, String content, int userId, int courseId, String topic, String url);

    @Insert("insert into posts (title,content, user_id, course_id, image_url) values (#{title},#{content}, #{userId}, #{courseId}, #{url})")
    void submitDiscussion1(String title, String content, int userId, int courseId, String url);

    @Insert("insert into posts (title,content, user_id, course_id, topic) values (#{title},#{content}, #{userId}, #{courseId}, #{topic})")
    void submitDiscussion2(String title, String content, int userId, int courseId, String topic);

    @Insert("insert into posts (title,content, user_id, course_id) values (#{title},#{content}, #{userId}, #{courseId})")
    void submitDiscussion3(String title, String content, int userId, int courseId);

    @Select("select posts.id,posts.title,posts.course_id,posts.topic,posts.user_id,posts.likes_count,posts.content,posts.created_at,posts.updated_at,posts.image_url,posts.is_favorite,posts.deleted,user.username,user.avatar from posts,user where posts.user_id= user.user_id and course_id=#{courseId}")
    List<Posts> getDiscussion(int courseId);

    @Select("select * from topics")
    List<Topics> getTopics();

    @Insert("insert into topics (name) values (#{topic})")
    void createTopic(String topic);

    @Update("update posts set likes_count = likes_count + 1 where id = #{postId}")
    void toggleLike(int postId);

    @Delete("delete from posts where id = #{postId}")
    void deletePost(int postId);

    @Select("select comments.id,post_id,comments.likes,user.user_id,user.avatar,user.username,parent_id,content,image_url,comments.created_at,comments.updated_at,deleted,root_id from comments,user where  comments.user_id= user.user_id and post_id =#{postId} ")
    List<Comments> fetch(int postId);

    @Select("select * from posts,user where posts.user_id=user.user_id and posts.id=#{postId}")
    Posts fetchPost(int postId);
    @Select("select comments.id,post_id,comments.likes,user.user_id,user.avatar,user.username,parent_id,content,image_url,comments.created_at,comments.updated_at,deleted,root_id from comments,user where  comments.user_id= user.user_id and id =#{id} ")
    Comments getUserBypId(int id);

    @Insert("insert into comments (post_id, user_id, content) values (#{postId}, #{userId}, #{comment})")
    void submitCommentAPI(int postId, String comment, int userId);

    @Update("update comments set likes = likes + 1 where id = #{id}")
    void likeComment(int id, int userId);

    @Insert("insert into comments (user_id, post_id, parent_id, content)values (#{userId}, #{postId}, #{id},#{reply})")
    void submitReply(int id, String reply, int postId, int userId);

    @Delete("delete from comments where id = #{id}")
    void deleteComment(int id);

    @Select("select user.user_id,user.username,user.role from user,course,studentcourseteacher where user.user_id = studentcourseteacher.student_id and course.course_id = studentcourseteacher.course_id and course.course_id = #{courseId} union select user.user_id,user.username,user.role from user,course where user.user_id =course.teacher_id and course.course_id=#{courseId}")
    List<User> fetchuser(int userId, int courseId);

    @Select("select user_id from user where username = #{username}")
    User getid(String username);

    @Select("select user.user_id,user.username from user,posts where posts.user_id =user.user_id and posts.id = #{postId}")
    User getpostUserBypId(int postId);

    @Select("select  course_id from posts where id = #{postId}")
    int getCourseId(int postId);

    @Select("select user_id from comments where id = #{id}")
    int getCommentsUserid(int id);

    @Select("select * from comments where id = #{id}")
    Comments fetchCommentsById(int id);
}
