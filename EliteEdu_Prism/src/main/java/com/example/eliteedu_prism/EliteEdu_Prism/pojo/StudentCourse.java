package com.example.eliteedu_prism.EliteEdu_Prism.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentCourse {

    private int studentId;
    private int courseId;
    private int studentCourseId;
    private String enrollmentDate;

}
