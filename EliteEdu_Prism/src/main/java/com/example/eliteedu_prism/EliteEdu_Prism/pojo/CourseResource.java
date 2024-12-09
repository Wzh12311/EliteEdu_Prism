package com.example.eliteedu_prism.EliteEdu_Prism.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResource {


    private int resourceId;
    private String courseId;
    private String resourceType;
    private String resourceUrl;

    private String uploadDate;

    private int isEditable;
    private int teacherId;
    private String resourceDescription;
}
