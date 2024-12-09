package com.example.eliteedu_prism.NoticeTest;


import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Attachments;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Course;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Notifications;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.User;
import com.example.eliteedu_prism.EliteEdu_Prism.service.NoticeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class notice {
    @Autowired
    private NoticeService noticeService;


    @Test
    public void getNotice(){


        List<Notifications> notice = noticeService.getNotice(1);

        System.out.println(notice);


    }
    @Test
    public void markAsRead(){

        noticeService.markAsRead("1",7);


    }

    @Test
    public void getCourses(){

        List<Course> course = noticeService.getCourses(1);

        System.out.println(course);
    }

    @Test
    public void getStudents(){

        List<User> user = noticeService.getStudents(1);

        System.out.println(user);

    }

    @Test
    public void getCourse(){

        Course course = noticeService.getCourse(1);

        System.out.println(course);
    }






}
