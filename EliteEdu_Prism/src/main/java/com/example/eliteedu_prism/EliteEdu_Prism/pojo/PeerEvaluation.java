package com.example.eliteedu_prism.EliteEdu_Prism.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeerEvaluation {

    private int peerEvaluationId;
    private int studentId;
    private int assignmentId;
    private String enrollmentDate;
    private String title;
    private String content;
    private int score;
    private int isSubmit;
    private int isGraded;
    private int grader;
    private String contentUrl;
    private String n1;
    private String n2;
}
