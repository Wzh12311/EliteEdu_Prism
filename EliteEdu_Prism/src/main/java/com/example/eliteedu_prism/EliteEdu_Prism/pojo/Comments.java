package com.example.eliteedu_prism.EliteEdu_Prism.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 评论实体类
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comments {

    private int id;
    private int postId;
    private int userId;
    private int parentId;
    private int rootId;
    private String content;
    private String imageUrl;
    private String createdAt;
    private String updatedAt;
    private String username;
    private String avatar;
    private int deleted;
    private int likes;
    private List<Comments> children;

}
