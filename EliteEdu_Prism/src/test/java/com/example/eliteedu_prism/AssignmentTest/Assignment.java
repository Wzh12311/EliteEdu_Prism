package com.example.eliteedu_prism.AssignmentTest;

import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Result;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.StudentAssignment;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.User;
import com.example.eliteedu_prism.EliteEdu_Prism.service.AssignmentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;




@SpringBootTest
public class Assignment {


    @Autowired
    AssignmentService assignmentService;

    @Test
    public void uploadAssignment(){

        List<Integer> allspstudent = assignmentService.allspstudent(33);

        System.out.println(allspstudent);

    }


    public void getSender(int courseId){

        User user = assignmentService.getSender(courseId);

        System.out.println(user);

    }

    @Test
    public void getSenderTest(){
        getSender(1);
    }



    @Test
    public void getAssignment(){
        {
            List<com.example.eliteedu_prism.EliteEdu_Prism.pojo.Assignment> assignments = assignmentService.getAssignments(1);

            System.out.println(assignments);
        }

    }

    @Test
    public void getPeerAssignments(){

        List<com.example.eliteedu_prism.EliteEdu_Prism.pojo.Assignment> assignments = assignmentService.getPeerAssignments(1);

        System.out.println(assignments);
    }

    @Test
    public void getTeacherId(){

        int teacherId = assignmentService.getTeacherId(1);

        System.out.println(teacherId);

    }

    @Test
    public void getPeerReviewAssignments() {
        List<StudentAssignment> studentAssignments = assignmentService.getPeerStudentSubmissions(1, 1);

        System.out.println(studentAssignments);
    }


    @Test
    public void uploadAssignment1(){


    }








}
