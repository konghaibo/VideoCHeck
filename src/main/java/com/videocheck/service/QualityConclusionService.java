package com.videocheck.service;

import com.videocheck.model.RuleCheckResult;

import java.util.List;

public interface QualityConclusionService {

    String buildConclusion(String transcription, List<RuleCheckResult> ruleResults);

    double score(List<RuleCheckResult> ruleResults);
}
