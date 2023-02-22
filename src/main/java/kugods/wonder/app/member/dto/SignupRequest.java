package kugods.wonder.app.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import kugods.wonder.app.member.entity.Member;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    @NotNull
    @Email
    @Size(max = 100)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    @Size(min = 3, max = 100)
    private String address;

    public static SignupRequest from(Member member) {
        if(member == null) return null;

        return SignupRequest.builder()
                .email(member.getEmail())
                .name(member.getName())
                .address(member.getAddress())
                .build();
    }
}

