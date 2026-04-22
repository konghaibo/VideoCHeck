package com.videocheck.service;

import com.videocheck.model.RuleCheckResult;
import com.videocheck.model.ScriptRule;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ScriptInspectionService {

    List<RuleCheckResult> inspect(MultipartFile videoFile, List<ScriptRule> rules);
}
