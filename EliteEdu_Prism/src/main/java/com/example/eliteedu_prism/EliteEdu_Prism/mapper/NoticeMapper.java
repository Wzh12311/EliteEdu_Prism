package com.example.eliteedu_prism.EliteEdu_Prism.mapper;


import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Course;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Notifications;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface NoticeMapper {

    @Select("select notifications.id,notifications.title,notifications.content,notifications.sender_id,notifications.type,notifications.created_at,notifications.post_id,notifications.course_id,user_notifications.status from notifications,user_notifications where  notifications.id=user_notifications.notification_id and user_notifications.user_id=#{userId} order by notifications.created_at desc")
    List<Notifications> getNotice(int userId);

    @Update("update user_notifications set status='read' where user_id=#{userId} and notification_id=#{notificationId}")
    void markAsRead(String userId, int notificationId);

    @Select("select * from course where  teacher_id=#{userId}")
    List<Course> getCourses(int userId);

    @Select("select  user.user_id,user.username,user.avatar,user.identification_number   from course,studentcourseteacher,user where course.course_id=studentcourseteacher.course_id and studentcourseteacher.student_id= user.user_id and course.course_id=#{selectedCourseId}")
    List<User> getStudents(int selectedCourseId);

    @Select("select * from course where course_id=#{courseId} ")
    Course getCourse(int courseId);
}
