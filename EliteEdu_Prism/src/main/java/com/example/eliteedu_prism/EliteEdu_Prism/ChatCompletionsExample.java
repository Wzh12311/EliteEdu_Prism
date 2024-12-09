package com.example.eliteedu_prism.EliteEdu_Prism;


import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ChatCompletionsExample {


    public static void main(String[] args) {

        String apiKey = System.getenv("ARK_API_KEY");
        ArkService service = new ArkService(apiKey);

        Scanner scanner = new Scanner(System.in);

        // 打印流式请求的初始提示
        System.out.println("\n----- streaming request -----");

        // 初始化消息列表
        final List<ChatMessage> streamMessages = new ArrayList<>();
        final ChatMessage streamSystemMessage = ChatMessage.builder()
                .role(ChatMessageRole.SYSTEM)
                .content("你是豆包，是由字节跳动开发的 AI 人工智能助手")
                .build();
        streamMessages.add(streamSystemMessage);

        // 启动一个循环，允许用户反复提问
        while (true) {
            // 获取用户输入
            System.out.print("请输入你的问题（输入 '退出' 来结束对话）：");
            String userInput = scanner.nextLine();

            // 如果输入是 "退出"，结束对话
            if ("退出".equalsIgnoreCase(userInput)) {
                System.out.println("结束对话。");
                break;
            }

            // 添加用户消息到消息列表
            final ChatMessage streamUserMessage = ChatMessage.builder()
                    .role(ChatMessageRole.USER)
                    .content(userInput)
                    .build();
            streamMessages.add(streamUserMessage);

            // 构建请求对象
            ChatCompletionRequest streamChatCompletionRequest = ChatCompletionRequest.builder()
                    .model("ep-20241106142047-5g46t")  // 使用指定的模型
                    .messages(streamMessages)  // 添加消息列表
                    .build();

            // 处理流式响应并打印结果
            service.streamChatCompletion(streamChatCompletionRequest)
                    .doOnError(Throwable::printStackTrace)
                    .blockingForEach(choice -> {
                        if (choice.getChoices().size() > 0) {
                            // 打印 AI 的回答，不换行
                            System.out.print(choice.getChoices().get(0).getMessage().getContent());
                        }
                    });

            // 你可以在这里添加 AI 的回答进入流消息列表
            // 例如：将 AI 的回应记录到对话历史中（如果需要的话）
            // final ChatMessage streamAiMessage = ChatMessage.builder()
            //         .role(ChatMessageRole.SYSTEM)
            //         .content(aiResponseBuilder.toString().trim())
            //         .build();
            // streamMessages.add(streamAiMessage);
        }

        // 关闭服务
        service.shutdownExecutor();

        // 关闭 Scanner
        scanner.close();
    }

}