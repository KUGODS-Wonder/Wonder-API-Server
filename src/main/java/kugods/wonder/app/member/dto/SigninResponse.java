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
public class SigninResponse {

    private Long memberId;

    private String token;

}
