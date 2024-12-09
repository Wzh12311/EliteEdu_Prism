package com.example.eliteedu_prism.EliteEdu_Prism.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 帖子或评论上传的链接
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Attachments {


    private int id;
    private int postId;
    private int commentId;
    private String fileUrl;
    private String uploadedAt;

}
