package com.videocheck.service;

import org.springframework.web.multipart.MultipartFile;

public interface SpeechRecognitionService {

    String transcribe(MultipartFile videoFile);
}
