package com.example.eliteedu_prism.EliteEdu_Prism.service.impl;

import com.example.eliteedu_prism.EliteEdu_Prism.mapper.AssignmentMapper;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.*;
import com.example.eliteedu_prism.EliteEdu_Prism.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    AssignmentMapper assignmentmapper;
    @Override
    public void uploadAssignment(Assignment assignment) {
        assignmentmapper.uploadAssignment(assignment);
    }

    @Override
    public List<Assignment> getAssignments(int courseId) {
        return assignmentmapper.getAssignments(courseId);
    }

    @Override
    public void submitStudentAssignment(StudentAssignment studentAssignment) {
        assignmentmapper.submitStudentAssignment(studentAssignment);
    }

    @Override
    public List<StudentAssignment> getStudentSubmissions(int courseId, int userId) {
        return assignmentmapper.getStudentSubmissions(courseId,userId);
    }

    @Override
    public List<StudentAssignment> getStudentSubmissions1(int courseId, int userId) {
        return assignmentmapper.getStudentSubmissions1(courseId,userId);
    }

    @Override
    public List<StudentAssignment> getStudentAssignments(int assignmentId) {
        return assignmentmapper.getStudentAssignments(assignmentId);
    }

    @Override
    public void gradeAssignment(int studentAssignmentId, int score,int grader) {
        assignmentmapper.gradeAssignment(studentAssignmentId,score,grader);
    }

    @Override
    public List<Integer> allspstudent(int assignmentId) {
        return assignmentmapper.allspstudent(assignmentId);
    }

    @Override
    public void assignPeerReview(int studentId, int assignmentId, int grader) {
        assignmentmapper.assignPeerReview(studentId,assignmentId,grader);
    }

    @Override
    public List<PeerEvaluation> getPeerReviewAssignments(int courseId, int userId) {
        return assignmentmapper.getPeerReviewAssignments(courseId,userId);
    }

    @Override
    public List<Assignment> getPeerAssignments(int courseId) {
        return assignmentmapper.getPeerAssignments(courseId);
    }

    @Override
    public void submitPeerStudentAssignment(PeerEvaluation studentAssignment) {
        assignmentmapper.submitPeerStudentAssignment(studentAssignment);
    }

    @Override
    public List<StudentAssignment> getPeerStudentSubmissions(int courseId, int userId) {
        return assignmentmapper.getPeerStudentSubmissions(courseId,userId);
    }

    @Override
    public void submitPeerReview(PeerEvaluation peerEvaluation) {
        assignmentmapper.submitPeerReview(peerEvaluation);
    }

    @Override
    public List<Assignment> getAssignments2(int courseId) {
        return assignmentmapper.getAssignments2(courseId);
    }

    @Override
    public int getTeacherId(int assignmentId) {
        return assignmentmapper.getTeacherId(assignmentId);
    }

    @Override
    public List<PeerEvaluation> getpeer(int assignmentId) {
        return assignmentmapper.getpeer(assignmentId);
    }

    @Override
    public User getSender(int courseId) {
        return assignmentmapper.getSender(courseId);
    }

    @Override
    public void Notice(Notifications notifications) {
        assignmentmapper.Notice(notifications);
    }

    @Override
    public List<User> getuser(int courseId) {
        return assignmentmapper.getuser(courseId);
    }

    @Override
    public void sendNotice(int id, int userId) {
        assignmentmapper.sendNotice(id,userId);
    }

    @Override
    public Assignment getAssignment(int assignmentId) {
        return assignmentmapper.getAssignment(assignmentId);
    }

    @Override
    public int getpeerId(int assignmentId, int userId) {
        return assignmentmapper.getpeerId(assignmentId,userId);
    }

    @Override
    public Assignment getAssignmentId(int studentAssignmentId) {
        return assignmentmapper.getAssignmentId(studentAssignmentId);
    }

    @Override
    public int getAssignmentstudent(int studentAssignmentId) {
        return assignmentmapper.getAssignmentstudent(studentAssignmentId);
    }

    @Override
    public Assignment getAssignmentPeer(int peerEvaluationId) {
        return assignmentmapper.getAssignmentPeer(peerEvaluationId);
    }

    @Override
    public int getAssignmentpeerstudent(int peerEvaluationId) {
        return assignmentmapper.getAssignmentpeerstudent(peerEvaluationId);
    }

    @Override
    public Comments getcomment(int id) {
        return assignmentmapper.getcomment(id);
    }


}
