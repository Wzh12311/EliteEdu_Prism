package com.example.eliteedu_prism.EliteEdu_Prism.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Course {

    private int courseId;
    private String courseName;
    private String courseDescription;
    private String syllabus;
    private String  calendarId;
    private int teacherId;
    private String username;
    private String courseImage;
    private String coursetime;
    private String openedAcademy;
    private int rating;



}
