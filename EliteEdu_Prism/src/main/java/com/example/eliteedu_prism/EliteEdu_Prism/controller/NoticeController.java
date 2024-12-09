package com.example.eliteedu_prism.EliteEdu_Prism.controller;


import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Course;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Notifications;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Result;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.User;
import com.example.eliteedu_prism.EliteEdu_Prism.service.AssignmentService;
import com.example.eliteedu_prism.EliteEdu_Prism.service.NoticeService;
import com.example.eliteedu_prism.EliteEdu_Prism.utils.GetTokenUserId;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.channels.NotYetBoundException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@Slf4j
public class NoticeController {

 @Autowired
 private NoticeService noticeService;
 @Autowired
 private AssignmentService assignmentService;

    @GetMapping("getNotice")
    public Result getNotice(HttpServletRequest request) {
        String userId = GetTokenUserId.getUserId(request);
        List<Notifications> list= noticeService.getNotice(Integer.parseInt(userId));

        return Result.success(list);

    }

    @PostMapping("markAsRead")
    public Result markAsRead(HttpServletRequest request, @RequestParam("notificationId") int notificationId)  {
        String userId = GetTokenUserId.getUserId(request);

        noticeService.markAsRead(userId, notificationId);
        return Result.success();
    }

    @GetMapping("getCourses")
    public Result getCourses(HttpServletRequest request) {
        String userId = GetTokenUserId.getUserId(request);
        List<Course> list =noticeService.getCourses(Integer.parseInt(userId));

        return Result.success(list);
    }

    @GetMapping("getStudents")
    public Result getStudents(HttpServletRequest request,@RequestParam("selectedCourseId") int selectedCourseId ){

        String userId = GetTokenUserId.getUserId(request);

        List<User> list = noticeService.getStudents(selectedCourseId);

        return Result.success(list);
    }
    @PostMapping("sendNotificationToStudents")
    public Result sendNotificationToStudents(HttpServletRequest request,@RequestParam("courseId") int courseId,@RequestParam("content") String content,@RequestParam("studentIds")List<Integer> studentIds) {
        String userId = GetTokenUserId.getUserId(request);//发送人
        System.out.println("课程号："+courseId);//获取信息
        Course course = noticeService.getCourse(courseId);
        System.out.println(course.getCourseName());
        System.out.println("内容：" +content); //通知内容
        System.out.println("学生列表：" +studentIds);//接收的人

        //todo 发送通知

        Notifications notifications =new Notifications();
        notifications.setType("assignment");
        notifications.setTitle("课程通知");
        notifications.setContent(course.getCourseName()+" 通知:"+content);
        notifications.setSenderId(Integer.parseInt(userId));
        notifications.setPostId(0);
        notifications.setCourseId(0);
        assignmentService.Notice(notifications);

        for (int i = 0; i < studentIds.size(); i++) {

            assignmentService.sendNotice(notifications.getId(), studentIds.get(i));

        }

        return Result.success();
    }
}
