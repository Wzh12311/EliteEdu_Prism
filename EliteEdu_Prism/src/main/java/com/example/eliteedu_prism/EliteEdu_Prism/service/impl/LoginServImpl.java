package com.example.eliteedu_prism.EliteEdu_Prism.service.impl;


import com.example.eliteedu_prism.EliteEdu_Prism.mapper.LoginMapper;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Result;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.User;
import com.example.eliteedu_prism.EliteEdu_Prism.service.LoginService;
import com.example.eliteedu_prism.EliteEdu_Prism.utils.AiUtils;
import com.example.eliteedu_prism.EliteEdu_Prism.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;
    //    @Override
//    public User login(User user) {
//        return loginMapper.login(user);
//    }



    @Override
    public String user_login(User user) {



        User u = loginMapper.login(user);
        if (u != null) {

            Map<String,Object> map=new HashMap<>();


            map.put("id",u.getUserId());
            map.put("username",u.getUsername());
            map.put("getIdentificationNumber",u.getIdentificationNumber());

            String jwt= JwtUtils.generateJwt(map);

            return jwt;


        } else
            return "用户名或密码错误";


    }
}
