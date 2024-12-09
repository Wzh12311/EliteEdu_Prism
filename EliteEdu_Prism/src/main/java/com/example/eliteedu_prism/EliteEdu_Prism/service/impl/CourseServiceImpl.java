package com.example.eliteedu_prism.EliteEdu_Prism.service.impl;

import com.example.eliteedu_prism.EliteEdu_Prism.mapper.CourseMapper;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Collection;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Course;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.CourseResource;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.User;
import com.example.eliteedu_prism.EliteEdu_Prism.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
@Autowired
CourseMapper courseMapper;


    @Override
    public List<Course> getCourseList(String userId) {

        return courseMapper.getCourseList(userId);

    }

    @Override
    public String getrole(String userId) {
        return courseMapper.getrole(userId);
    }

    @Override
    public List<Course> getCourseList2(String userId) {
        return courseMapper.getCourseList2(userId);
    }

    @Override
    public Course getspcourse(int courseId, int teacherId) {
        return courseMapper.getspcourse(courseId,teacherId);
    }

    @Override
    public void updateCourseInfo(int courseId, int teacherId, Course courseData) {
        courseMapper.updateCourseInfo(courseId,teacherId,courseData);
    }

    @Override
    public List<CourseResource> getCoursewares(int courseId, int teacherId) {
        return courseMapper.getCoursewares(courseId,teacherId);
    }

    @Override
    public void uploadCourseware(int courseId, int teacherId, String url, String resourceDescription) {
         courseMapper.uploadCourseware(courseId,teacherId,url,resourceDescription);
    }

    @Override
    public void deleteCourseware(int courseId, int teacherId, int resourceId) {
        courseMapper.deleteCourseware(courseId,teacherId,resourceId);
    }

    @Override
    public List<Collection> getCollection(String userId) {
        return courseMapper.getCollection(Integer.parseInt(userId));
    }

    @Override
    public CourseResource getResource(int resourceId) {
        return courseMapper.getResource(resourceId);
    }

    @Override
    public void addFavorite(int collectionId, String resourceDescription, String resourceUrl) {
        courseMapper.addFavorite(collectionId,resourceDescription,resourceUrl);
    }

    @Override
    public User getTeacherInfo(String userId, String courseId, String teacherId) {
        return courseMapper.getTeacherInfo(Integer.parseInt(userId),Integer.parseInt(courseId),Integer.parseInt(teacherId));
    }

    @Override
    public Course getCourseSyllabusInfo(String userId, String courseId, String teacherId) {
        return courseMapper.getCourseSyllabusInfo(Integer.parseInt(userId),Integer.parseInt(courseId),Integer.parseInt(teacherId));
    }

    @Override
    public void updateTeacher(int teacherId, String profilePic) {
         courseMapper.updateTeacher(teacherId,profilePic);
    }

    @Override
    public void updateOutline(int courseId, Course courseData) {
        courseMapper.updateOutline(courseId,courseData);
    }

    @Override
    public List<CourseResource> getCourseexam(int courseId, int teacherId) {
        return courseMapper.getCourseexam(courseId,teacherId);
    }

    @Override
    public void uploadCourseexam(int courseId, int teacherId, String url, String resourceDescription) {
        courseMapper.uploadCourseexam(courseId,teacherId,url,resourceDescription);
    }

    @Override
    public List<CourseResource> getCourseexercise(int courseId, int teacherId) {
        return courseMapper.getCourseexercise(courseId,teacherId);
    }

    @Override
    public void uploadCourseexercise(int courseId, int teacherId, String url, String resourceDescription) {
        courseMapper.uploadCourseexercise(courseId,teacherId,url,resourceDescription);
    }
}
