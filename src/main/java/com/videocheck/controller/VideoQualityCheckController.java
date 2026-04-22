package com.videocheck.controller;

import com.videocheck.dto.QualityCheckResponse;
import com.videocheck.model.ScriptRule;
import com.videocheck.service.VideoQualityCheckOrchestrator;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/quality-check")
public class VideoQualityCheckController {

    private final VideoQualityCheckOrchestrator orchestrator;

    public VideoQualityCheckController(VideoQualityCheckOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    @PostMapping(value = "/video", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public QualityCheckResponse qualityCheck(@RequestPart("video") MultipartFile video,
                                             @Valid @RequestPart("rules") List<@Valid ScriptRule> rules) {
        return orchestrator.execute(video, rules);
    }
}
