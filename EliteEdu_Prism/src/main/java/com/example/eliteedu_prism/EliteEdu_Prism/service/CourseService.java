package com.example.eliteedu_prism.EliteEdu_Prism.service;

import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Collection;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Course;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.CourseResource;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.User;

import java.util.List;

public interface CourseService {
    List<Course> getCourseList(String userId);

    String getrole(String userId);

    List<Course> getCourseList2(String userId);

    Course getspcourse(int courseId, int teacherId);

    void updateCourseInfo(int courseId, int teacherId, Course courseData);

    List<CourseResource> getCoursewares(int courseId, int teacherId);

    void uploadCourseware(int courseId, int teacherId, String url, String resourceDescription);

    void deleteCourseware(int courseId, int teacherId, int resourceId);

    List<Collection> getCollection(String userId);

    CourseResource getResource(int resourceId);

    void addFavorite(int collectionId, String resourceDescription, String resourceUrl);

    User getTeacherInfo(String userId, String courseId, String teacherId);

    Course getCourseSyllabusInfo(String userId, String courseId, String teacherId);

    void updateTeacher(int teacherId, String profilePic);

    void updateOutline(int courseId, Course courseData);

    List<CourseResource> getCourseexam(int courseId, int teacherId);

    void uploadCourseexam(int courseId, int teacherId, String url, String resourceDescription);

    List<CourseResource> getCourseexercise(int courseId, int teacherId);

    void uploadCourseexercise(int courseId, int teacherId, String url, String resourceDescription);
}
