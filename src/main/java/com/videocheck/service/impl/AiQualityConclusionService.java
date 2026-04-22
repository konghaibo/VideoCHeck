package com.videocheck.service.impl;

import com.videocheck.model.RuleCheckResult;
import com.videocheck.service.QualityConclusionService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiQualityConclusionService implements QualityConclusionService {

    private final ChatClient chatClient;

    public AiQualityConclusionService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public String buildConclusion(String transcription, List<RuleCheckResult> ruleResults) {
        String prompt = "请根据逐字稿和规则命中结果输出简洁质检结论，包含优点、问题、整改建议。"
                + "逐字稿=" + transcription + "，规则结果=" + ruleResults;

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }

    @Override
    public double score(List<RuleCheckResult> ruleResults) {
        if (ruleResults == null || ruleResults.isEmpty()) {
            return 0D;
        }
        long passCount = ruleResults.stream().filter(RuleCheckResult::pass).count();
        return (double) passCount * 100 / ruleResults.size();
    }
}
