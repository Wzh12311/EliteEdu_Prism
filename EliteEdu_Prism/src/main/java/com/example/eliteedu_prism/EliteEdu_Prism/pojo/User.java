package com.example.eliteedu_prism.EliteEdu_Prism.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userId;
    private String username;
    private String email;
    private String password;
    private String address;
    private String role;
    private String profilePic;
    private String registrationDate;
    private int identificationNumber;
    private String avatar;// 头像
    private boolean isfollowed = false;

}
