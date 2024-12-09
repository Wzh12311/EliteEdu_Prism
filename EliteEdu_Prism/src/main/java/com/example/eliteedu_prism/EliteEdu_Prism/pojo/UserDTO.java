package com.example.eliteedu_prism.EliteEdu_Prism.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private User user;  // 原来的 User 对象
    private boolean isFollowed;  // 是否关注的状态

    // 构造函数

}
