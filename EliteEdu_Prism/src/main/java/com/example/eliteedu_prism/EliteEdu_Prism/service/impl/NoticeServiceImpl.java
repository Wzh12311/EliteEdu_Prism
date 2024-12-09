package com.example.eliteedu_prism.EliteEdu_Prism.service.impl;

import com.example.eliteedu_prism.EliteEdu_Prism.mapper.NoticeMapper;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Course;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Notifications;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.User;
import com.example.eliteedu_prism.EliteEdu_Prism.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService{
  @Autowired
  private NoticeMapper noticeMapper;

    @Override
    public List<Notifications> getNotice(int userId) {
        return noticeMapper.getNotice(userId);
    }

  @Override
  public void markAsRead(String userId, int notificationId) {
    noticeMapper.markAsRead(userId,notificationId);
  }

  @Override
  public List<Course> getCourses(int userId) {
    return noticeMapper.getCourses(userId);
  }

  @Override
  public List<User> getStudents(int selectedCourseId) {
    return noticeMapper.getStudents(selectedCourseId);
  }

  @Override
  public Course getCourse(int courseId) {
    return noticeMapper.getCourse(courseId);
  }
}
