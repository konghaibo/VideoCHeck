package com.videocheck.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.videocheck.model.RuleCheckResult;
import com.videocheck.model.ScriptRule;
import com.videocheck.service.ScriptInspectionService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AiScriptInspectionService implements ScriptInspectionService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public AiScriptInspectionService(ChatClient.Builder chatClientBuilder, ObjectMapper objectMapper) {
        this.chatClient = chatClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    @Override
    public List<RuleCheckResult> inspect(MultipartFile videoFile, List<ScriptRule> rules) {
        String prompt = "你是话术质检助手。输入是视频文件名和质检规则。请输出JSON数组，字段为"
                + "ruleCode/pass/comment。文件名=" + videoFile.getOriginalFilename()
                + "，规则=" + rules;

        String content = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        try {
            return objectMapper.readValue(content, new TypeReference<>() {
            });
        } catch (Exception ex) {
            return rules.stream()
                    .map(rule -> new RuleCheckResult(rule.ruleCode(), false, "模型返回格式异常: " + ex.getMessage()))
                    .toList();
        }
    }
}
