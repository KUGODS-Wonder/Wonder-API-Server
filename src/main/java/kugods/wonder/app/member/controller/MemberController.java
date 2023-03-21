package kugods.wonder.app.member.controller;

import kugods.wonder.app.common.dto.ApiDataResponse;
import kugods.wonder.app.member.dto.MemberProfileResponse;
import kugods.wonder.app.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/profile")
    public ApiDataResponse<MemberProfileResponse> getProfile(
            Authentication auth
    ) {
        return ApiDataResponse.of(memberService.getProfile(auth.getName()));
    }
}
