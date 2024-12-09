package com.example.eliteedu_prism.EliteEdu_Prism.controller;

import com.example.eliteedu_prism.EliteEdu_Prism.pojo.*;
import com.example.eliteedu_prism.EliteEdu_Prism.service.AssignmentService;
import com.example.eliteedu_prism.EliteEdu_Prism.service.LoginService;
import com.example.eliteedu_prism.EliteEdu_Prism.service.UserService;
import com.example.eliteedu_prism.EliteEdu_Prism.utils.AliOSSUtils;
import com.example.eliteedu_prism.EliteEdu_Prism.utils.GetTokenUserId;
import com.example.eliteedu_prism.EliteEdu_Prism.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@CrossOrigin
@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userservice;

    @Autowired
    private AssignmentService   assignmentService;

    @Autowired
    private AliOSSUtils aliOSSUtils;

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping("/getUserInfo")
    public Result getUserInfo(@RequestParam("token") String token) {
        try {

            Claims claims = JwtUtils.parseJwt(token); // 从 JWT 中获取用户 ID

            String userId = claims.get("id").toString();
            System.out.println(claims);
            System.out.println(userId);
            // 2. 从数据库中获取用户信息
            User user = userservice.getUserById(Integer.parseInt(userId)); // 使用服务类获取用户信息
            System.out.println(user);
            if (user == null) {
                return Result.error("用户不存在");
            }

            // 3. 将用户信息封装到一个 Map 或 DTO 对象中（也可以直接返回 User 对象）
//           Map<String, Object> userInfo = new HashMap<>();
//           userInfo.put("id", user.getId());
//           userInfo.put("name", user.getName());

            // 头像URL

            // 4. 返回用户信息
            return Result.success(user);


        } catch (Exception e) {
            return Result.error("Token解析失败或用户信息获取失败");
        }


    }

    /**
     * 更新用户信息
     *
     * @param user
     * @param token
     * @return
     */

    @PutMapping("/updateUserInfo")
    public Result updateUser(@RequestBody User user, @RequestParam("token") String token) {


//        System.out.println(user);
        userservice.updateUser(user);

        return Result.success();


    }

    /**
     * 更改密码
     *
     * @param passwordRequest
     * @param request
     * @return
     */
    @PutMapping("/changePassword")
    public Result changePassword(@RequestBody ChangePasswordRequest passwordRequest, HttpServletRequest request) {

        try {
            // 获取 Authorization 头中的 Token
            String userId = GetTokenUserId.getUserId(request);

            if (userId == null) {
                return Result.error("Token invalid");
            }
            // 获取当前用户信息
            User user = userservice.getUserById(Integer.parseInt(userId));
            if (user == null) {
                return Result.error("User not found");
            }

            // 校验旧密码是否正确
            if (!userservice.checkPassword(user, passwordRequest.getOldPassword())) {
                return Result.error("Old password is incorrect");
            }

            // 修改密码
            boolean success = userservice.changePassword(userId, passwordRequest.getNewPassword());
            if (success) {
                return Result.success();
            } else {
                return Result.error("Failed to update password");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("Internal server error");
        }


    }

    /**
     * 上传个人头像
     *
     * @param request
     * @param avatar
     * @return
     */
    @PostMapping("/upload-avatar")
    public Result uploadAvatar(HttpServletRequest request, @RequestPart("avatar") MultipartFile avatar) {

        String userId = GetTokenUserId.getUserId(request);

        try {
            String url = aliOSSUtils.upload(avatar);
            userservice.updateAvatar(userId, url);
            return Result.success();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @GetMapping("/getAllUsers")
    public Result getAllUsers(HttpServletRequest request) {
        String userId = GetTokenUserId.getUserId(request);
//        List<User> list = userservice.getAllUsers(userId);//所有用户（除自己）
//        List<User> list1 = userservice.getFollowedUsers(userId);//获取关注的用户
//        for (int i = 0; i < list.size(); i++) {
//
//            if(list1.contains(list.get(i))){
//                list.get(i).setIsfollowed(true);
//            }else {
//                list.get(i).setIsfollowed(false);
//            }
//        }
        List<User> allUsers = userservice.getAllUsers(userId);
        // 获取当前用户已经关注的用户列表，并将其存入 Set 以加快查找速度
        List<User> followedUsers = userservice.getFollowedUsers(userId);
        Set<Integer> followedUserIds = followedUsers.stream()
                .map(User::getUserId)  // 只提取用户的 userId
                .collect(Collectors.toSet());

        // 为每个用户设置 isFollowed 字段
        for (User user : allUsers) {
            user.setIsfollowed(followedUserIds.contains(user.getUserId()));
        }
        System.out.println(allUsers);
        return Result.success(allUsers);
    }


    @GetMapping("/getFollowedUsers")
    public Result getFollowedUsers(HttpServletRequest request) {
        String userId = GetTokenUserId.getUserId(request);
        List<User> list = userservice.getFollowedUsers(userId);
        return Result.success(list);
    }

    @PostMapping("/followUserApi")
    public Result followUser(HttpServletRequest request,@RequestBody Map<String, String> body){
        String userId = body.get("userId");
        String myuserId = GetTokenUserId.getUserId(request);
        userservice.followUser(myuserId, userId);
        User userProfile = userservice.getUserProfile(myuserId);
        User userProfile1 = userservice.getUserProfile(userId);

        Notifications notifications =new Notifications();
        notifications.setType("interaction");
        notifications.setTitle("新用户关注");
        notifications.setSenderId(userProfile.getUserId());
        notifications.setContent("用户:"+userProfile.getUsername()+"关注了您");
        notifications.setPostId(0);
        notifications.setCourseId(0);
        assignmentService.Notice(notifications);
        assignmentService.sendNotice(notifications.getId(),userProfile1.getUserId());



        return Result.success();
    }

    @PostMapping("/unfollowUser")
    public Result unfollowUser(HttpServletRequest request,@RequestBody Map<String, String> body){

        String myuserId = GetTokenUserId.getUserId(request);
        String userId = body.get("userId");
        userservice.unfollowUser(myuserId, userId);
        return Result.success();

    }

    @GetMapping("/getUserProfile")
    public Result getUserProfile(HttpServletRequest request,@RequestParam("userId") String userId){
        User user = userservice.getUserProfile(userId);
        return Result.success(user);

    }

    @GetMapping("getunreadNotificationsCount")
    public Result getunreadNotificationsCount(HttpServletRequest request){
        String userId = GetTokenUserId.getUserId(request);
        int count = userservice.getunreadNotificationsCount(userId);
        return Result.success(count);
    }


}
