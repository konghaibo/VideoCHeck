# VideoCHeck

基于 Spring AI Alibaba（DashScope OpenAI Compatible 接口）的后端示例，实现：

1. 用户上传视频文件
2. 智能体并行执行：
   - 语音识别（ASR）
   - 话术质检（基于系统规则）
3. 综合评判并生成质检结论

## 技术设计

- `VideoQualityCheckController`：接收视频和规则。
- `VideoQualityCheckOrchestrator`：使用 `CompletableFuture` 并行调用 ASR 与话术质检。
- `AliyunSpeechRecognitionService`：调用模型完成语音转写。
- `AiScriptInspectionService`：根据规则执行话术检查并返回结构化结果。
- `AiQualityConclusionService`：基于转写与规则结果生成总结与评分。

## 运行

```bash
export DASHSCOPE_API_KEY=your_key
mvn spring-boot:run
```

## 接口

`POST /api/quality-check/video`，`multipart/form-data`：

- `video`: 视频文件
- `rules`: JSON 数组，例如：

```json
[
  {
    "ruleCode": "OPENING",
    "description": "开场必须先问候客户",
    "expectedExpression": "您好"
  }
]
```
