package kugods.wonder.app.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupResponse {

    private Long memberId;

    private String email;

    private String name;

    private String address;

    private String token;

}
