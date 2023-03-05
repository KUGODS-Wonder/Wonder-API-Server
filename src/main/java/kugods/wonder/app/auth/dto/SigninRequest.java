package kugods.wonder.app.auth.dto;


import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SigninRequest {

    @NotNull
    @Email
    @Size(max = 100)
    private String email;

    @NotNull
    @Size(min = 3, max = 100)
    private String password;
}