package kugods.wonder.app.record.controller;

import kugods.wonder.app.common.dto.ApiDataResponse;
import kugods.wonder.app.record.dto.BookmarkRequest;
import kugods.wonder.app.record.dto.BookmarkResponse;
import kugods.wonder.app.record.dto.CompletionRequest;
import kugods.wonder.app.record.dto.CompletionResponse;
import kugods.wonder.app.record.service.CompletionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/completions")
public class CompletionController {

    private final CompletionService completionService;

    @PostMapping
    public ApiDataResponse<CompletionResponse> addCompletion(
            Authentication auth,
            @Validated @RequestBody CompletionRequest request
    ) {
        return ApiDataResponse.of(completionService.addCompletion(auth.getName(), request));
    }
}
