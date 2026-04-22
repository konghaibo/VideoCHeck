package com.videocheck.service.impl;

import com.videocheck.service.SpeechRecognitionService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AliyunSpeechRecognitionService implements SpeechRecognitionService {

    private final ChatClient chatClient;

    public AliyunSpeechRecognitionService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public String transcribe(MultipartFile videoFile) {
        String prompt = "你是语音识别助手。请根据视频语音内容返回完整逐字稿。如果无法直接处理二进制文件，"
                + "请给出\"需接入ASR服务\"提示和推荐的ASR参数。文件名:" + videoFile.getOriginalFilename();

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}
