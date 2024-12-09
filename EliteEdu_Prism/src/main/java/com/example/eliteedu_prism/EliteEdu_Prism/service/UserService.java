package com.example.eliteedu_prism.EliteEdu_Prism.service;

import com.example.eliteedu_prism.EliteEdu_Prism.pojo.User;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.UserDTO;
import com.example.eliteedu_prism.EliteEdu_Prism.utils.JwtUtils;

import java.util.List;

public interface UserService {


    public User  getUserById(int id);

    void updateUser(User user);

    /**
     * 校验旧密码是否正确
     * @param user
     * @param oldPassword
     * @return
     */
    boolean checkPassword(User user, String oldPassword);

    boolean changePassword(String userId, String newPassword);

    void updateAvatar(String userId, String url);

    List<User> getAllUsers(String userId);

    List<User> getFollowedUsers(String userId);

    void followUser(String myuserId, String userId);


    void unfollowUser(String myuserId, String userId);

    User getUserProfile(String userId);

    int getunreadNotificationsCount(String userId);
}
