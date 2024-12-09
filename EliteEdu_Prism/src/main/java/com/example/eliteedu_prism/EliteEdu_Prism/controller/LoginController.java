package com.example.eliteedu_prism.EliteEdu_Prism.controller;


import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Result;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.User;
import com.example.eliteedu_prism.EliteEdu_Prism.service.LoginService;
import com.example.eliteedu_prism.EliteEdu_Prism.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitRetryTemplateCustomizer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@CrossOrigin
@Slf4j
@RestController
public class LoginController

{
    @Autowired
    LoginService loginService;

    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    // 登录控制器
    public Result login(@RequestBody User user){

        log.info("用户登录，用户账号：{}，密码：{}", user.getIdentificationNumber(), user.getPassword());

        String  message = loginService.user_login(user);

        if(Objects.equals(message, "用户名或密码错误")){
            return Result.error("用户名或密码错误");
        }
        else {
            return Result.success(message);
        }

    }


}

