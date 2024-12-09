package com.example.eliteedu_prism.EliteEdu_Prism.controller;


import com.example.eliteedu_prism.EliteEdu_Prism.pojo.*;
import com.example.eliteedu_prism.EliteEdu_Prism.service.AssignmentService;
import com.example.eliteedu_prism.EliteEdu_Prism.service.CourseService;
import com.example.eliteedu_prism.EliteEdu_Prism.service.UserService;
import com.example.eliteedu_prism.EliteEdu_Prism.utils.AliOSSUtils;
import com.example.eliteedu_prism.EliteEdu_Prism.utils.GetTokenUserId;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


@CrossOrigin
@RestController
@Slf4j
public class AssignmentController {

    @Autowired
    AliOSSUtils aliOSSUtils;

    @Autowired
    AssignmentService assignmentService;

    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;


    @PutMapping("/uploadAssignment")
    public Result uploadAssignment(@RequestParam("title") String title,
                                   @RequestParam("description") String description,
                                   @RequestParam("courseId") int courseId,
                                   @RequestParam("dueDate") String dueDate,
                                   @RequestParam("maxScore") int maxScore,
                                   @RequestParam("content") MultipartFile file,
                                   @RequestParam("type") String type) throws IOException {

        String content = aliOSSUtils.upload(file);
        Assignment assignment = new Assignment();
        assignment.setContent(content);
        assignment.setTitle(title);
        assignment.setDescription(description);
        assignment.setCourseId(courseId);
        assignment.setDueDate(dueDate);
        assignment.setMaxScore(maxScore);

        /*
        远程调用上传作业，仅调用 uploadAssignment 没有用
         */
        assignmentService.uploadAssignment(assignment);

        //上传作业的同时需要分配作业任务，传到数据库中
        if (type.equals("peer")) {
            Random rand = new Random();
            ArrayList<Integer> arrayList = new ArrayList<>();
            int index;
            List<Integer> list = assignmentService.allspstudent(assignment.getAssignmentId());
            for (Integer i = 0; i < list.size(); i++) {//1 2 5 6 7 8
                int student_id = list.get(i);//1
                index = rand.nextInt(list.size());
                while (index == i || arrayList.contains(index)) {
                    index = rand.nextInt(list.size());
                }
                arrayList.add(index);
                int grader = list.get(index);//0-5随机取值

                assignmentService.assignPeerReview(student_id, assignment.getAssignmentId(), grader);

            }
        }
        //通知中心发布通知，写到服务层
        User user = assignmentService.getSender(courseId);
        Course course = courseService.getspcourse(courseId, user.getUserId());
        Notifications notifications = new Notifications();
        notifications.setType("assignment");
        notifications.setTitle("新作业发布");
        notifications.setSenderId(user.getUserId());
        notifications.setPostId(0);
        notifications.setCourseId(0);
        if (type.equals("peer")) {
            notifications.setContent(course.getCourseName() + " 课程通知:" + user.getUsername() + "发布了新互评作业:" + assignment.getTitle());
        } else {
            notifications.setContent(course.getCourseName() + " 课程通知:" + user.getUsername() + "发布了新作业:" + assignment.getTitle());
        }

        assignmentService.Notice(notifications);

        //接收的用户
        List<User> list = assignmentService.getuser(courseId);
        for (User user1 : list) {
            assignmentService.sendNotice(notifications.getId(), user1.getUserId());
        }


        return Result.success();
    }


    @GetMapping("/getAssignments")
    public Result getAssignments(@RequestParam("courseId") int courseId) {

        List<Assignment> assignments = assignmentService.getAssignments(courseId);
        return Result.success(assignments);
    }

    @GetMapping("/getAssignments2")
    public Result getAssignments2(@RequestParam("courseId") int courseId) {

        List<Assignment> assignments = assignmentService.getAssignments2(courseId);
        return Result.success(assignments);
    }

    @GetMapping("/getPeerAssignments")
    public Result getPeerAssignments(@RequestParam("courseId") int courseId) {

        List<Assignment> assignments = assignmentService.getPeerAssignments(courseId);
        return Result.success(assignments);
    }

    @PutMapping("/submitStudentAssignment")
    public Result submitStudentAssignment(@RequestParam("assignmentId") int assignmentId,
                                          @RequestParam("content") String content,
                                          @RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        String userId = GetTokenUserId.getUserId(request);
        String url = aliOSSUtils.upload(file);
        StudentAssignment studentAssignment = new StudentAssignment();
        studentAssignment.setContentUrl(url);
        studentAssignment.setAssignmentId(assignmentId);
        studentAssignment.setStudentId(Integer.parseInt(userId));
        studentAssignment.setContent(content); // TODO: get student id from context
        studentAssignment.setIsSubmit(1); // TODO: get student id from context
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //System.out.println(formatter.format(date));
        studentAssignment.setEnrollmentDate(formatter.format(date)); // TODO: get student id from context


        int teacherId = assignmentService.getTeacherId(assignmentId);
        studentAssignment.setGrader(teacherId);

        try {
            assignmentService.submitStudentAssignment(studentAssignment);
        } catch (Exception e) {

            e.printStackTrace();
            return Result.error("提交失败");
        }
        User userProfile = userService.getUserProfile(userId);
        Assignment assignment = assignmentService.getAssignment(assignmentId);
        Notifications notifications = new Notifications();
        notifications.setType("submission");
        notifications.setTitle("作业提交");
        notifications.setSenderId(userProfile.getUserId());
        notifications.setPostId(0);
        notifications.setCourseId(0);
        notifications.setContent(userProfile.getUsername() + "提交了作业:" + assignment.getTitle());
        assignmentService.Notice(notifications);
        int userId1 = assignmentService.getTeacherId(assignmentId);
        //接收的用户
        assignmentService.sendNotice(notifications.getId(), userId1);
        return Result.success();
    }

    @PutMapping("/submitPeerStudentAssignment")
    public Result submitPeerStudentAssignment(@RequestParam("assignmentId") int assignmentId,
                                              @RequestParam("content") String content,
                                              @RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        String userId = GetTokenUserId.getUserId(request);
        String url = aliOSSUtils.upload(file);
        PeerEvaluation studentAssignment = new PeerEvaluation();
        studentAssignment.setContentUrl(url);
        studentAssignment.setAssignmentId(assignmentId);
        studentAssignment.setStudentId(Integer.parseInt(userId));
        studentAssignment.setContent(content); // TODO: get student id from context
        studentAssignment.setIsSubmit(1); // TODO: get student id from context
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //System.out.println(formatter.format(date));
        studentAssignment.setEnrollmentDate(formatter.format(date)); // TODO: get student id from context

        assignmentService.submitPeerStudentAssignment(studentAssignment);

        User userProfile = userService.getUserProfile(userId);
        Assignment assignment = assignmentService.getAssignment(assignmentId);
        Notifications notifications = new Notifications();
        notifications.setType("submission");
        notifications.setTitle("作业提交");
        notifications.setSenderId(userProfile.getUserId());
        notifications.setPostId(0);
        notifications.setCourseId(0);
        notifications.setContent(userProfile.getUsername() + "提交了互评作业:" + assignment.getTitle());
        assignmentService.Notice(notifications);
        int userId1 = assignmentService.getpeerId(assignmentId, Integer.parseInt(userId));
        //接收的用户
        assignmentService.sendNotice(notifications.getId(), userId1);

        return Result.success();
    }

    @GetMapping("/getStudentSubmissions")
    public Result getStudentSubmissions(@RequestParam("courseId") int courseId, HttpServletRequest request) {
        String userId = GetTokenUserId.getUserId(request);
        List<StudentAssignment> studentAssignments = assignmentService.getStudentSubmissions(courseId, Integer.parseInt(userId));

        return Result.success(studentAssignments);
    }


    @GetMapping("/getPeerStudentSubmissions")
    public Result getPeerStudentSubmissions(@RequestParam("courseId") int courseId, HttpServletRequest request) {
        String userId = GetTokenUserId.getUserId(request);
        List<StudentAssignment> studentAssignments = assignmentService.getPeerStudentSubmissions(courseId, Integer.parseInt(userId));

        return Result.success(studentAssignments);
    }


    @GetMapping("/getStudentAssignments")
    public Result getStudentAssignments(@RequestParam("assignmentId") int assignmentId) {

        List<StudentAssignment> studentAssignments = assignmentService.getStudentAssignments(assignmentId);

        return Result.success(studentAssignments);

    }

    @PostMapping("/gradeAssignment")
    public Result gradeAssignment(@RequestBody StudentAssignment studentAssignment, HttpServletRequest request) {
        String userId = GetTokenUserId.getUserId(request);
        int studentAssignmentId = studentAssignment.getStudentAssignmentId();
        int score = studentAssignment.getScore();
        assignmentService.gradeAssignment(studentAssignmentId, score, Integer.parseInt(userId));

        Assignment assignment = assignmentService.getAssignmentId(studentAssignmentId);
        User userProfile = userService.getUserProfile(userId);
//        Assignment assignment = assignmentService.getAssignment(assignmentId);
        Notifications notifications = new Notifications();
        notifications.setType("grading");
        notifications.setTitle("作业评分");
        notifications.setSenderId(userProfile.getUserId());
        notifications.setPostId(0);
        notifications.setCourseId(0);
        notifications.setContent(userProfile.getUsername() + "评判了你的作业:" + assignment.getTitle() + " 分数：" + score);
        assignmentService.Notice(notifications);
        int userId1 = assignmentService.getAssignmentstudent(studentAssignmentId);
        //接收的用户
        assignmentService.sendNotice(notifications.getId(), userId1);

        return Result.success();
    }

    @PostMapping("/assignPeerReview")
    public Result assignPeerReview(@RequestParam("assignmentId") int assignmentId) {
        Random rand = new Random();
        ArrayList<Integer> arrayList = new ArrayList<>();
        int index;
        List<Integer> list = assignmentService.allspstudent(assignmentId);
        for (Integer i = 0; i < list.size(); i++) {//1 2 5 6 7 8
            int student_id = list.get(i);//1
            index = rand.nextInt(list.size());
            while (index == i || arrayList.contains(index)) {
                index = rand.nextInt(list.size());
            }
            arrayList.add(index);
            int grader = list.get(index);//0-5随机取值

            assignmentService.assignPeerReview(student_id, assignmentId, grader);
        }
        return Result.success();
    }

    @GetMapping("/getPeerReviewAssignments")
    public Result getPeerReviewAssignments(@RequestParam("courseId") int courseId, HttpServletRequest request) {
        String userId = GetTokenUserId.getUserId(request);
        List<PeerEvaluation> peerEvaluations = assignmentService.getPeerReviewAssignments(courseId, Integer.parseInt(userId));

        return Result.success(peerEvaluations);
    }

    @PostMapping("/submitPeerReview")
    public Result submitPeerReview(@RequestBody PeerEvaluation peerEvaluation, HttpServletRequest request) {
        assignmentService.submitPeerReview(peerEvaluation);
        String userId = GetTokenUserId.getUserId(request);
        Assignment assignment = assignmentService.getAssignmentPeer(peerEvaluation.getPeerEvaluationId());
        User userProfile = userService.getUserProfile(userId);
//        Assignment assignment = assignmentService.getAssignment(assignmentId);
        Notifications notifications = new Notifications();
        notifications.setType("grading");
        notifications.setTitle("作业评分");
        notifications.setSenderId(userProfile.getUserId());
        notifications.setPostId(0);
        notifications.setCourseId(0);
        notifications.setContent(userProfile.getUsername() + "评判了你的互评作业:" + assignment.getTitle() + " 分数：" + peerEvaluation.getScore());
        assignmentService.Notice(notifications);
        int userId1 = assignmentService.getAssignmentpeerstudent(peerEvaluation.getPeerEvaluationId());
        //接收的用户
        assignmentService.sendNotice(notifications.getId(), userId1);


        return Result.success();

    }

    @GetMapping("/getpeer")
    public Result getpeer(@RequestParam("assignmentId") int assignmentId) {


        List<PeerEvaluation> list = assignmentService.getpeer(assignmentId);
        return Result.success(list);
    }


}
