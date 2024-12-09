package com.example.eliteedu_prism.EliteEdu_Prism.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Assignment", description = "$!{table.comment}")
public class Assignment {
    @Schema(description = "assignmentId")
    private int assignmentId;
    @Schema(description = "课程号")
    private int courseId;
    @Schema(description = "作业标题")
    private String title;
    @Schema(description = "作业描述")
    private String description;
    @Schema(description = "截止日期")
    private String dueDate;
    @Schema(description = "最大分数")
    private int maxScore;
    @Schema(description = "作业链接")
    private String content;
    @Schema(description = "作业类型")
    private String type;


}
