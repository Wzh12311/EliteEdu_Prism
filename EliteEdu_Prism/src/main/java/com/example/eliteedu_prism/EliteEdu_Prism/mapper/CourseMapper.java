package com.example.eliteedu_prism.EliteEdu_Prism.mapper;

import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Collection;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Course;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.CourseResource;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface CourseMapper {
    @Select("select course.course_id,course.teacher_id,course_name, course_description, calendar_id, user.username from course, user where course.teacher_id=user.user_id and user_id=#{userId}")
    List<Course> getCourseList(String userId);

    @Select("select role from user where user_id=#{userId}")
    String getrole(String userId);

    @Select( "select course.course_id,course.teacher_id,course_name, course_description, calendar_id, u2.username from course, user u1, user u2, studentcourseteacher where u1.user_id = studentcourseteacher.student_id and course.course_id = studentcourseteacher.course_id  and u2.user_id = course.teacher_id and u1.user_id = #{userId}")
    List<Course> getCourseList2(String userId);


    @Select("select * from course where course_id=#{courseId} and teacher_id=#{teacherId}")
    Course getspcourse(int courseId, int teacherId);

    @Update("update course set course_name=#{courseData.courseName}, course_description=#{courseData.courseDescription} where course_id=#{courseId} and teacher_id=#{teacherId}")
    void updateCourseInfo(int courseId, int teacherId, Course courseData);

    @Select("select * from courseresource where course_id=#{courseId} and teacher_id=#{teacherId} and resource_type='courseware' ")
    List<CourseResource> getCoursewares(int courseId, int teacherId);


    @Insert("insert into courseresource(course_id, teacher_id, resource_type, resource_url,is_editable,resource_description) values(#{courseId}, #{teacherId}, 'courseware', #{url},1,#{resourceDescription})")
    void uploadCourseware(int courseId, int teacherId, String url,String resourceDescription);

    @Delete("delete from courseresource where resource_id=#{resourceId}")
    void deleteCourseware(int courseId, int teacherId, int resourceId);

    @Select("select * from collection where user_id=#{userId}")
    List<Collection> getCollection(int userId);

    @Select("select * from courseresource where resource_id=#{resourceId}")
    CourseResource getResource(int resourceId);

    @Insert("insert into collection_content(collection_id,content_type,title, description, content) values(#{collectionId},'link','课程资源', #{resourceDescription}, #{resourceUrl})")
    void addFavorite(int collectionId, String resourceDescription, String resourceUrl);

    @Select("select * from user where user_id=#{teacherId}")
    User getTeacherInfo(int userId, int courseId, int teacherId);

    @Select( "select * from course where course_id=#{courseId} and teacher_id=#{teacherId}")
    Course getCourseSyllabusInfo(int userId, int courseId, int teacherId);

    @Update("update user set profile_pic=#{profilePic} where user_id=#{teacherId}")
    void updateTeacher(int teacherId, String profilePic);

    @Update("update course set syllabus=#{courseData.syllabus} where course_id=#{courseId} ")
    void updateOutline(int courseId, Course courseData);

    @Select("select * from courseresource where course_id=#{courseId} and teacher_id=#{teacherId} and resource_type='exam' ")
    List<CourseResource> getCourseexam(int courseId, int teacherId);


    @Insert("insert into courseresource(course_id, teacher_id, resource_type, resource_url,is_editable,resource_description) values(#{courseId}, #{teacherId}, 'exam', #{url},1,#{resourceDescription})")
    void uploadCourseexam(int courseId, int teacherId, String url, String resourceDescription);

    @Select("select * from courseresource where course_id=#{courseId} and teacher_id=#{teacherId} and resource_type='exercise' ")
    List<CourseResource> getCourseexercise(int courseId, int teacherId);

    @Insert("insert into courseresource(course_id, teacher_id, resource_type, resource_url,is_editable,resource_description) values(#{courseId}, #{teacherId}, 'exercise', #{url},1,#{resourceDescription})")
    void uploadCourseexercise(int courseId, int teacherId, String url, String resourceDescription);
}

