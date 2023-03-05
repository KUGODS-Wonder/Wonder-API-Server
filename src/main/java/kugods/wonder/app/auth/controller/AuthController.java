package kugods.wonder.app.auth.controller;

import kugods.wonder.app.common.dto.ApiDataResponse;
import kugods.wonder.app.auth.dto.*;
import kugods.wonder.app.member.repository.MemberRepository;
import kugods.wonder.app.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class AuthController {

    private final MemberRepository memberRepository;
    private final AuthService authService;

    @PostMapping("/signin")
    public ApiDataResponse<SigninResponse> signin(
            @Validated @RequestBody SigninRequest request
    ) {
        return ApiDataResponse.of(authService.signin(request));
    }

    @PostMapping("/signup")
    public ApiDataResponse<SignupResponse> signup(
            @Validated @RequestBody SignupRequest request
    ) {
        return ApiDataResponse.of(authService.signup(request));
    }

    @PostMapping("/google/login")
    public ApiDataResponse<SigninResponse> googleLogin(
            @Validated @RequestBody OauthLoginReqeust request,
            @RequestHeader("GOOGLE-TOKEN") String googleToken
    ) {
        return ApiDataResponse.of(authService.googleLogin(request, googleToken));
    }
}
