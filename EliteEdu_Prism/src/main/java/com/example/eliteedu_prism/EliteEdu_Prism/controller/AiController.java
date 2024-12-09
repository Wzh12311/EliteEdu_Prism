package com.example.eliteedu_prism.EliteEdu_Prism.controller;


import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Result;
import com.example.eliteedu_prism.EliteEdu_Prism.service.AiService;
import com.example.eliteedu_prism.EliteEdu_Prism.utils.AiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.http.POST;

@RestController
@Slf4j
@CrossOrigin
public class AiController {

    @Autowired
    AiUtils aiUtils;

    @Autowired
    AiService aiService;

    @PostMapping("chat")
    public Result chat(@RequestParam("message") String message){

        String  reply = aiService.getReply(message);

        return  Result.success(reply);
    }


}
