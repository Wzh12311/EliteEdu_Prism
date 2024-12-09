package com.example.eliteedu_prism.EliteEdu_Prism.service.impl;


import com.example.eliteedu_prism.EliteEdu_Prism.mapper.UserMapper;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.User;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.UserDTO;
import com.example.eliteedu_prism.EliteEdu_Prism.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(int id) {
        return userMapper.getUserById(id);
    }

    /**
     * todo test
     * @param user
     */
    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public boolean checkPassword(User user, String oldPassword) {

        if(user.getPassword().equals(oldPassword))
        {
        return true;
        }
        return false;

    }

    @Override
    public boolean changePassword(String userId, String newPassword) {
        return userMapper.changePassword(Integer.parseInt(userId),newPassword);
    }

    @Override
    public void updateAvatar(String userId, String url) {
         userMapper.updateAvatar(Integer.parseInt(userId),url);
    }

    @Override
    public List<User> getAllUsers(String userId) {
        return userMapper.getAllUsers(Integer.parseInt(userId));
    }

    @Override
    public List<User> getFollowedUsers(String userId) {
        return userMapper.getFollowedUsers(Integer.parseInt(userId));
    }

    @Override
    public void followUser(String myuserId, String userId) {
        userMapper.followUser(Integer.parseInt(myuserId),Integer.parseInt(userId));
    }

    @Override
    public void unfollowUser(String myuserId, String userId) {
        userMapper.unfollowUser(Integer.parseInt(myuserId),Integer.parseInt(userId));
    }

    @Override
    public User getUserProfile(String userId) {
        return userMapper.getUserProfile(Integer.parseInt(userId));
    }

    @Override
    public int getunreadNotificationsCount(String userId) {
        return userMapper.getunreadNotificationsCount(Integer.parseInt(userId));
    }
}
