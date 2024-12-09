package com.example.eliteedu_prism.EliteEdu_Prism.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionContent {
    private int contentId;
    private int collectionId;
    private String contentType;
    private String title;
    private String description;
    private String createdAt;
    private String updatedAt;
    private String content;

}
