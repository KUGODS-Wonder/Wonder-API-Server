package kugods.wonder.app.member.controller;

import kugods.wonder.app.common.dto.ApiDataResponse;
import kugods.wonder.app.member.dto.SigninRequest;
import kugods.wonder.app.member.dto.SigninResponse;
import kugods.wonder.app.member.dto.SignupRequest;
import kugods.wonder.app.member.dto.SignupResponse;
import kugods.wonder.app.member.repository.MemberRepository;
import kugods.wonder.app.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @PostMapping("/signin")
    public ApiDataResponse<SigninResponse> signin(
            @RequestBody SigninRequest request
    ) {
        return ApiDataResponse.of(memberService.signin(request));
    }

    @PostMapping("/signup")
    public ApiDataResponse<SignupResponse> signup(
            @RequestBody SignupRequest request
    ) {
        return ApiDataResponse.of(memberService.signup(request));
    }
}
