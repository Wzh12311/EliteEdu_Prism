package com.example.eliteedu_prism.EliteEdu_Prism.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 帖子实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Posts {

    private int id;
    private String content;
    private String topic;
    private String imageUrl;
    private String username;
    private String createdAt;
    private String updatedAt;
    private String avatar;
    private String title;
    private int deleted;
    private int courseId;
    private int userId;
    private int isFavorite;
    private int likesCount;
}
