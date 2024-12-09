package com.example.eliteedu_prism.EliteEdu_Prism.mapper;

import com.example.eliteedu_prism.EliteEdu_Prism.pojo.User;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.UserDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user where user_id=#{id}")
    User getUserById(int id );


    @Update("update user set username = #{username},email = #{email} where user_id = #{userId}")
    void updateUser(User user);




    @Update("update user set password = #{newPassword} where user_id = #{userId}")
    boolean changePassword(int userId, String newPassword);

    @Update("update user set avatar = #{url} where user_id = #{userId}")
    void updateAvatar(int userId, String url);

    @Select("select * from user where user_id not in (select user_id from user where user_id =#{userId})")
    List<User> getAllUsers(int userId);


    @Select("select user.user_id, user.username,user.avatar ,user.identification_number  from user_follow,user where following_id=user.user_id and follower_id = #{userId}")
    List<User> getFollowedUsers(int userId);

    @Insert("insert into user_follow(follower_id,following_id) values(#{myuserId},#{userId})")
    void followUser(int myuserId, int userId);

    @Delete("delete from user_follow where follower_id = #{myuserId} and following_id = #{userId}")
    void unfollowUser(int myuserId, int userId);

    @Select("select * from user where user_id = #{userId}")
    User getUserProfile(int userId);

    @Select("select count(*) from user_notifications where user_id=#{userId} and status='unread'")
    int getunreadNotificationsCount(int userId);
}
