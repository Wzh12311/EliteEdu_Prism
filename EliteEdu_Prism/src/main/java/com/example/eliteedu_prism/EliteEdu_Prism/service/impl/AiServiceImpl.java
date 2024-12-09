package com.example.eliteedu_prism.EliteEdu_Prism.service.impl;

import com.example.eliteedu_prism.EliteEdu_Prism.service.AiService;
import com.example.eliteedu_prism.EliteEdu_Prism.utils.AiUtils;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AiServiceImpl implements AiService {
    @Autowired
    AiUtils aiUtils;

    @Override
    public String getReply(String message) {

        aiUtils.ChatCompletionsService("5c648105-9e49-4a78-a1b2-ee8fcc7c02ad");
        return aiUtils.getAIResponse(message);
    }
}
