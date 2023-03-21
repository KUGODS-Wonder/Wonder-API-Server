package kugods.wonder.app.member.service;

import kugods.wonder.app.member.dto.MemberProfileResponse;

public interface MemberService {
    MemberProfileResponse getProfile(String email);
}
