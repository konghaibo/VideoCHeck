package com.videocheck.dto;

import com.videocheck.model.RuleCheckResult;

import java.util.List;

public record QualityCheckResponse(
        String transcription,
        List<RuleCheckResult> ruleResults,
        String finalConclusion,
        double score
) {
}
