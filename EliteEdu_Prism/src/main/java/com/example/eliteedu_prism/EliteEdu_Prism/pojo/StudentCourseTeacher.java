package com.example.eliteedu_prism.EliteEdu_Prism.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseTeacher {

    private int studentId;
    private int courseId;
    private int teacherId;
    private int studentCourseTeacherId;

    private String recentDate;
}
