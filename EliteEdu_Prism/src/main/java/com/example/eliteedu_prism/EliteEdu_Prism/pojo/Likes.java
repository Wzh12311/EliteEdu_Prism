package com.example.eliteedu_prism.EliteEdu_Prism.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 评论帖子点赞实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Likes {

    private int id;
    private int postId;
    private int userId;
    private int commentId;
    private int createdAt;
}
