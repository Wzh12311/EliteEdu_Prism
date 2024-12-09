package com.example.eliteedu_prism.UserTest;

import com.example.eliteedu_prism.EliteEdu_Prism.mapper.UserMapper;
import com.example.eliteedu_prism.EliteEdu_Prism.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class User {

    @Autowired
    private UserService userService;

    @Test
    public void getUserById() {
        com.example.eliteedu_prism.EliteEdu_Prism.pojo.User u = userService.getUserById(1);

        System.out.println(u);

    }

    @Test
    public void checkPassword() {
        com.example.eliteedu_prism.EliteEdu_Prism.pojo.User user = new com.example.eliteedu_prism.EliteEdu_Prism.pojo.User();
        user.setPassword("11111111");
        user.setIdentificationNumber(1000001);
        String  oldpassword="22222222";
        String  oldpassword2="11111111";

        boolean b = userService.checkPassword(user, oldpassword);
        System.out.println(b);
        boolean b1 = userService.checkPassword(user, oldpassword2);
        System.out.println(b1);


    }

    @Test
    public void getFollowedUsers(){

        List<com.example.eliteedu_prism.EliteEdu_Prism.pojo.User> followedUsers = userService.getFollowedUsers("1");
        System.out.println("followedUsers = " + followedUsers);
    }

    @Test
    public void getAllUsers(){

        List<com.example.eliteedu_prism.EliteEdu_Prism.pojo.User> allUsers = userService.getAllUsers("1");
        System.out.println("allUsers = " + allUsers);
    }

    @Test
    public void getUserProfile(){
        com.example.eliteedu_prism.EliteEdu_Prism.pojo.User userProfile = userService.getUserProfile("1");
        System.out.println("userProfile = " + userProfile);
    }

    @Test
    public void getunreadNotificationsCount(){
        int unreadNotificationsCount = userService.getunreadNotificationsCount("1");
        System.out.println("unreadNotificationsCount = " + unreadNotificationsCount);
    }

    /**
     * 关注用户
     */
    @Test
    public void followUser(){
        userService.followUser("1","6");

    }

    /**
     * 取关用户
     */
    @Test
    public void unfollowUser(){
        userService.unfollowUser("1","6");
    }

    @Test
    public void updateAvatar(){
        userService.updateAvatar("6","https://avatar-wzh.oss-cn-beijing.aliyuncs.com/4063f217-7d28-4c5a-b355-27f5f03a6476.jpg");
    }

    /**
     * 不需要更改密码，只用测试
     */
    @Test
    public void changePassword() {
        userService.changePassword("1", "11111111");
    }


}
