package com.videocheck.model;

import jakarta.validation.constraints.NotBlank;

public record ScriptRule(
        @NotBlank(message = "ruleCode不能为空")
        String ruleCode,
        @NotBlank(message = "description不能为空")
        String description,
        String expectedExpression
) {
}
