package com.example.eliteedu_prism.EliteEdu_Prism.service;

import com.example.eliteedu_prism.EliteEdu_Prism.pojo.*;

import java.util.List;

public interface AssignmentService {
    void uploadAssignment(Assignment assignment);

    List<Assignment> getAssignments(int courseId);

    void submitStudentAssignment(StudentAssignment studentAssignment);

    List<StudentAssignment> getStudentSubmissions(int courseId, int userId);
    List<StudentAssignment> getStudentSubmissions1(int courseId, int userId);

    List<StudentAssignment> getStudentAssignments(int assignmentId);

    void gradeAssignment(int studentAssignmentId, int score,int grader);

    List<Integer> allspstudent(int assignmentId);

    void assignPeerReview(int studentId, int assignmentId, int grader);

    List<PeerEvaluation> getPeerReviewAssignments(int courseId, int userId);


    List<Assignment> getPeerAssignments(int courseId);

    void submitPeerStudentAssignment(PeerEvaluation studentAssignment);

    List<StudentAssignment> getPeerStudentSubmissions(int courseId, int userId);

    void submitPeerReview(PeerEvaluation peerEvaluation);

    List<Assignment> getAssignments2(int courseId);

    int getTeacherId(int assignmentId);

    List<PeerEvaluation> getpeer(int assignmentId);

    User getSender(int courseId);

    void Notice(Notifications notifications);

    List<User> getuser(int courseId);

    void sendNotice(int id, int userId);

    Assignment getAssignment(int assignmentId);

    int getpeerId(int assignmentId, int i);

    Assignment getAssignmentId(int studentAssignmentId);

    int getAssignmentstudent(int studentAssignmentId);

    Assignment getAssignmentPeer(int peerEvaluationId);

    int getAssignmentpeerstudent(int peerEvaluationId);

    Comments getcomment(int id);
}
