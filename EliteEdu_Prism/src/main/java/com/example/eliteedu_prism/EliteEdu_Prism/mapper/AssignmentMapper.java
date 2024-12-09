package com.example.eliteedu_prism.EliteEdu_Prism.mapper;

import com.example.eliteedu_prism.EliteEdu_Prism.pojo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AssignmentMapper {

    @Insert("insert into assignment (course_id,title,description,due_date,max_score,content,type) values(#{courseId},#{title},#{description},#{dueDate},#{maxScore},#{content},#{type})" )
    @Options(useGeneratedKeys = true, keyProperty = "assignmentId")
    void uploadAssignment(Assignment assignment);

    @Select("select * from assignment where course_id=#{courseId} and type='teacher'")
    List<Assignment> getAssignments(int courseId);

    @Insert("insert into studentassignment (assignment_id,student_id,enrollment_date,content,content_url,is_submit,grader) values(#{assignmentId},#{studentId},#{enrollmentDate},#{content},#{contentUrl},#{isSubmit},#{grader})")
    void submitStudentAssignment(StudentAssignment studentAssignment);

    @Select("select student_assignment_id,student_id,studentassignment.assignment_id,enrollment_date,studentassignment.content,content_url,score,is_submit,assignment.title,u1.username as n1,u2.username as n2 from studentassignment, assignment,user as u1, user as u2 where studentassignment.assignment_id = assignment.assignment_id and u1.user_id=studentassignment.student_id and u2.user_id=grader and student_id =#{userId} and course_id=#{courseId}")
    List<StudentAssignment> getStudentSubmissions(int courseId, int userId);
    @Select("select student_assignment_id,student_id,studentassignment.assignment_id,enrollment_date,studentassignment.content,content_url,score,is_submit,assignment.title from studentassignment, assignment where studentassignment.assignment_id = assignment.assignment_id and  student_id =#{userId} and course_id=#{courseId}")
    List<StudentAssignment> getStudentSubmissions1(int courseId, int userId);
    @Select("select studentassignment.student_assignment_id, student_id, assignment_id, enrollment_date, content, content_url, score, is_submit, is_graded, grader,studentassignment.score,studentassignment.assignment_id,studentassignment.enrollment_date,studentassignment.content,studentassignment.content_url,studentassignment.score,studentassignment.is_submit,studentassignment.is_graded,u1.username as n1,u2.username as n2 from studentassignment,user as u1, user as u2 where u1.user_id = studentassignment.student_id and u2.user_id = studentassignment.grader and assignment_id = #{assignmentId}")
    List<StudentAssignment> getStudentAssignments(int assignmentId);

    @Update("update studentassignment set score=#{score},is_graded=1 ,grader=#{grader} where student_assignment_id=#{studentAssignmentId}")
    void gradeAssignment(int studentAssignmentId, int score,int grader);

    @Select("select studentcourseteacher.student_id from studentcourseteacher,assignment where studentcourseteacher.course_id =assignment.course_id and assignment.assignment_id = #{assignmentId}")
    List<Integer> allspstudent(int assignmentId);

    @Insert("insert into peer_evaluation (student_id,assignment_id,grader) values(#{studentId},#{assignmentId},#{grader})")
    void assignPeerReview(int studentId, int assignmentId, int grader);

    @Select("select peer_evaluation.peer_evaluation_id,peer_evaluation.score,assignment.title,peer_evaluation.student_id, peer_evaluation.content, peer_evaluation.content_url,is_graded,user.username as n1 from peer_evaluation,assignment,user where assignment.assignment_id = peer_evaluation.assignment_id and user.user_id=peer_evaluation.student_id and assignment.course_id =#{courseId} and grader =#{userId}")
    List<PeerEvaluation> getPeerReviewAssignments(int courseId, int userId);


    @Select("select * from assignment where course_id=#{courseId} and type='peer'")
    List<Assignment> getPeerAssignments(int courseId);


    @Update("update peer_evaluation set enrollment_date=#{enrollmentDate},content=#{content},content_url=#{contentUrl},is_submit=#{isSubmit} where student_id=#{studentId} and assignment_id=#{assignmentId}")
    void submitPeerStudentAssignment(PeerEvaluation studentAssignment);

    @Select("select peer_evaluation_id,student_id,peer_evaluation.assignment_id,enrollment_date,peer_evaluation.content,content_url,score,is_submit,is_graded,assignment.title,u1.username as n1,u2.username as n2 from peer_evaluation,assignment,user as u1,user as u2 where peer_evaluation.assignment_id = assignment.assignment_id and u1.user_id = peer_evaluation.student_id and u2.user_id = grader and student_id =#{userId} and course_id=#{courseId}")
    List<StudentAssignment> getPeerStudentSubmissions(int courseId, int userId);

    @Update("update peer_evaluation set score=#{score},is_graded=1  where peer_evaluation_id=#{peerEvaluationId}")
    void submitPeerReview(PeerEvaluation peerEvaluation);

    @Select("select * from assignment where course_id=#{courseId} and type='peer'")
    List<Assignment> getAssignments2(int courseId);

    @Select("select course.teacher_id from course,assignment where assignment.course_id=course.course_id and assignment.assignment_id=#{assignmentId}")
    int getTeacherId(int assignmentId);

    @Select("select peer_evaluation.peer_evaluation_id,peer_evaluation.score,peer_evaluation.assignment_id,peer_evaluation.enrollment_date,peer_evaluation.content,peer_evaluation.content_url,peer_evaluation.score,peer_evaluation.is_submit,peer_evaluation.is_graded,u1.username as n1,u2.username as n2 from peer_evaluation,user as u1, user as u2 where u1.user_id = peer_evaluation.student_id and u2.user_id = peer_evaluation.grader and assignment_id = #{assignmentId}")
    List<PeerEvaluation> getpeer(int assignmentId);

    @Select("select user.user_id,user.username from course,user where user.user_id=course.teacher_id and course_id=#{courseId}")
    User getSender(int courseId);

    @Insert("insert into notifications (sender_id,type,title,content,post_id,course_id) values (#{senderId},#{type},#{title},#{content},#{postId},#{courseId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void Notice(Notifications notifications);

    @Select("select user.user_id,user.username from user,studentcourseteacher where  user.user_id=studentcourseteacher.student_id  and studentcourseteacher.course_id=#{courseId}")
    List<User> getuser(int courseId);

    @Insert("insert into user_notifications (user_id,notification_id) values (#{userId},#{id})")
    void sendNotice(int id, int userId);

    @Select("select * from assignment where assignment_id=#{assignmentId}")
    Assignment getAssignment(int assignmentId);

    @Select("select peer_evaluation.grader from peer_evaluation where assignment_id=#{assignmentId} and student_id=#{userId}")
    int getpeerId(int assignmentId, int userId);

    @Select("select assignment.assignment_id,assignment.course_id,assignment.title from assignment,studentassignment where assignment.assignment_id=studentassignment.assignment_id and studentassignment.student_assignment_id=#{studentAssignmentId}")
    Assignment getAssignmentId(int studentAssignmentId);

    @Select("select student_id from studentassignment where student_assignment_id=#{studentAssignmentId}")
    int getAssignmentstudent(int studentAssignmentId);

    @Select("select assignment.assignment_id,assignment.course_id,assignment.title from assignment,peer_evaluation where assignment.assignment_id=peer_evaluation.assignment_id and peer_evaluation.peer_evaluation_id=#{peerEvaluationId}")
    Assignment getAssignmentPeer(int peerEvaluationId);

    @Select("select student_id from peer_evaluation where peer_evaluation_id=#{peerEvaluationId}")
    int getAssignmentpeerstudent(int peerEvaluationId);

    @Select("select * from comments where id=#{id}")
    Comments getcomment(int id);
}
