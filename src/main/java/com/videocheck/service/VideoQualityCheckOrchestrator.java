package com.videocheck.service;

import com.videocheck.dto.QualityCheckResponse;
import com.videocheck.model.RuleCheckResult;
import com.videocheck.model.ScriptRule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class VideoQualityCheckOrchestrator {

    private final SpeechRecognitionService speechRecognitionService;
    private final ScriptInspectionService scriptInspectionService;
    private final QualityConclusionService qualityConclusionService;
    private final Executor qualityCheckExecutor;

    public VideoQualityCheckOrchestrator(SpeechRecognitionService speechRecognitionService,
                                         ScriptInspectionService scriptInspectionService,
                                         QualityConclusionService qualityConclusionService,
                                         @Qualifier("qualityCheckExecutor") Executor qualityCheckExecutor) {
        this.speechRecognitionService = speechRecognitionService;
        this.scriptInspectionService = scriptInspectionService;
        this.qualityConclusionService = qualityConclusionService;
        this.qualityCheckExecutor = qualityCheckExecutor;
    }

    public QualityCheckResponse execute(MultipartFile videoFile, List<ScriptRule> rules) {
        CompletableFuture<String> transcriptionFuture = CompletableFuture.supplyAsync(
                () -> speechRecognitionService.transcribe(videoFile), qualityCheckExecutor);

        CompletableFuture<List<RuleCheckResult>> inspectionFuture = CompletableFuture.supplyAsync(
                () -> scriptInspectionService.inspect(videoFile, rules), qualityCheckExecutor);

        CompletableFuture.allOf(transcriptionFuture, inspectionFuture).join();

        String transcription = transcriptionFuture.join();
        List<RuleCheckResult> ruleResults = inspectionFuture.join();

        String finalConclusion = qualityConclusionService.buildConclusion(transcription, ruleResults);
        double score = qualityConclusionService.score(ruleResults);

        return new QualityCheckResponse(transcription, ruleResults, finalConclusion, score);
    }
}
