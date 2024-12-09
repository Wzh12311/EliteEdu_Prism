package com.example.eliteedu_prism.EliteEdu_Prism.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collection {
    private int collectionId;
    private int userId;
    private int isPrivate;
    private String collectionName;
    private String collectionDescription;
    private String createdAt;
    private String updatedAt;
    private int likes;


}
