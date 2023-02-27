package kugods.wonder.app.member.controller;

import kugods.wonder.app.auth.oauth2.dto.GoogleLoginClientResponse;
import kugods.wonder.app.common.dto.ApiDataResponse;
import kugods.wonder.app.member.dto.*;
import kugods.wonder.app.member.exception.InvalidGoogleToken;
import kugods.wonder.app.member.repository.MemberRepository;
import kugods.wonder.app.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import static kugods.wonder.app.auth.oauth2.GoogleUserInfoProvider.getGoogleLoginClientResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    @Value("${google.auth.url}")
    private String googleAuthUrl;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @PostMapping("/signin")
    public ApiDataResponse<SigninResponse> signin(
            @Validated @RequestBody SigninRequest request
    ) {
        return ApiDataResponse.of(memberService.signin(request));
    }

    @PostMapping("/signup")
    public ApiDataResponse<SignupResponse> signup(
            @Validated @RequestBody SignupRequest request
    ) {
        return ApiDataResponse.of(memberService.signup(request));
    }

    @PostMapping("/google/login")
    public ApiDataResponse<SigninResponse> googleLogin(
            @Validated @RequestBody OauthLoginReqeust request,
            @RequestHeader("GOOGLE-TOKEN") String googleToken
    ) {
        validateGoogleToken(googleToken); // jwt Filter가 아닌, google 발급 유효성 검증만 진행
        return ApiDataResponse.of(memberService.googleLogin(request));
    }

    private void validateGoogleToken(String googleToken) {
        try {
            getGoogleLoginClientResponse(googleAuthUrl, googleToken);
        } catch (Exception e) {
            throw new InvalidGoogleToken();
        }
    }
}
