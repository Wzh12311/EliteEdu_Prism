package com.example.eliteedu_prism.EliteEdu_Prism.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 帖子评论通知
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notifications {

    private int id;
    private int senderId;
    private int courseId;
    private int postId;
    private String type;
    private String title;
    private String content;
    private String createdAt;
    private String status;

}
