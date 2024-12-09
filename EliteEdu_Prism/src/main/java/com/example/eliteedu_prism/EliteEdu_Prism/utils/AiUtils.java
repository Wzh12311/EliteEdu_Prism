// ChatCompletionsService.java
package com.example.eliteedu_prism.EliteEdu_Prism.utils;

import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AiUtils {

    private ArkService service;

    public void ChatCompletionsService(String apiKey) {
        this.service = new ArkService(apiKey);
    }

    public String getAIResponse(String userInput) {
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(ChatMessage.builder()
                .role(ChatMessageRole.SYSTEM)
                .content("你是豆包，是由字节跳动开发的 AI 人工智能助手")
                .build());

        messages.add(ChatMessage.builder()
                .role(ChatMessageRole.USER)
                .content(userInput)
                .build());

        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("ep-20241106142047-5g46t")  // 使用指定的模型
                .messages(messages)
                .build();

        final StringBuilder aiResponse = new StringBuilder();

        service.streamChatCompletion(request)
                .doOnError(Throwable::printStackTrace)
                .blockingForEach(choice -> {
                    if (choice.getChoices().size() > 0) {
                        aiResponse.append(choice.getChoices().get(0).getMessage().getContent());
                    }
                });

        return aiResponse.toString();
    }

    public void shutdown() {
        service.shutdownExecutor();
    }
}
