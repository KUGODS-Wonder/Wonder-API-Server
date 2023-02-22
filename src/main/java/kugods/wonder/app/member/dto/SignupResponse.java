package kugods.wonder.app.member.dto;

import kugods.wonder.app.member.entity.Authority;
import kugods.wonder.app.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupResponse {

    private Long memberId;

    private String email;

    private String name;

    private String address;

    private List<Authority> roles = new ArrayList<>();

    private String token;

}
