package com.example.eliteedu_prism.EliteEdu_Prism.service;

import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Course;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Notifications;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.User;

import java.util.List;

public interface NoticeService {
    List<Notifications> getNotice(int userId);

    void markAsRead(String userId, int notificationId);

    List<Course> getCourses(int userId);

    List<User> getStudents(int selectedCourseId);

    Course getCourse(int courseId);
}
