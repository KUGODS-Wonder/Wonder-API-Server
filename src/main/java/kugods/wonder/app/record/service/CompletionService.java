package kugods.wonder.app.record.service;

import kugods.wonder.app.record.dto.CompletionRequest;
import kugods.wonder.app.record.dto.CompletionResponse;

public interface CompletionService {
    CompletionResponse addCompletion(String email, CompletionRequest request);
}
