package com.example.eliteedu_prism.EliteEdu_Prism.utils;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;


public class OpenAiUtil {
    @Value("${openai.secret_key}")
    private String token;

    private OpenAiService service;

    @PostConstruct
    public void init(){
        service= new OpenAiService(token, Duration.ofSeconds(60L));
    }
    public List<CompletionChoice> sendComplete(String prompt) {
        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("text-davinci-003") //采用最强模型，达芬奇模型3
                .maxTokens(1500)
                .prompt(prompt)
                .user("testing")
                .logitBias(new HashMap<>())
                .build();

        return service.createCompletion(completionRequest).getChoices();
    }
}