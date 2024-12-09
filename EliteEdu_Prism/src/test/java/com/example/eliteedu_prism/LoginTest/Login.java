package com.example.eliteedu_prism.LoginTest;


import com.example.eliteedu_prism.EliteEdu_Prism.pojo.User;
import com.example.eliteedu_prism.EliteEdu_Prism.service.LoginService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@SpringBootTest
public class Login {
    @Autowired
    LoginService loginService;

    @Test
    public void login_test(){
        User user= new User();
        user.setIdentificationNumber(1000001);
        user.setPassword("11111111");
        String mesg = loginService.user_login(user);

        System.out.println("mesg = " + mesg);


        User user1= new User();
        user1.setIdentificationNumber(1000001);
        user1.setPassword("1111111");
        String mesg1 = loginService.user_login(user1);

        System.out.println("mesg1 = " + mesg1);


    }
}
