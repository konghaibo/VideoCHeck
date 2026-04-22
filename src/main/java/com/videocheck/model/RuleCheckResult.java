package com.videocheck.model;

public record RuleCheckResult(
        String ruleCode,
        boolean pass,
        String comment
) {
}
