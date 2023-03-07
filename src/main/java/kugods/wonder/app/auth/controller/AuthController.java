package kugods.wonder.app.auth.controller;

import kugods.wonder.app.auth.dto.*;
import kugods.wonder.app.auth.service.AuthServiceImpl;
import kugods.wonder.app.common.dto.ApiDataResponse;
import kugods.wonder.app.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final MemberRepository memberRepository;
    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/signin")
    public ApiDataResponse<SigninResponse> signin(
            @Validated @RequestBody SigninRequest request
    ) {
        return ApiDataResponse.of(authServiceImpl.signin(request));
    }

    @PostMapping("/signup")
    public ApiDataResponse<SignupResponse> signup(
            @Validated @RequestBody SignupRequest request
    ) {
        return ApiDataResponse.of(authServiceImpl.signup(request));
    }

    @PostMapping("/google/login")
    public ApiDataResponse<SigninResponse> googleLogin(
            @Validated @RequestBody OauthLoginReqeust request,
            @RequestHeader("GOOGLE-TOKEN") String googleToken
    ) {
        return ApiDataResponse.of(authServiceImpl.googleLogin(request, googleToken));
    }

    @GetMapping("/check-name/{name}")
    public ApiDataResponse<CheckNameResponse> checkName(
            @PathVariable("name") String name
    ) {
        return ApiDataResponse.of(authServiceImpl.checkName(name));
    }
}
