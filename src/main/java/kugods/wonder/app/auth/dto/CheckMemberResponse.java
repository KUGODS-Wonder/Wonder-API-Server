package kugods.wonder.app.auth.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckMemberResponse {
    private String email;
    private boolean isRegistered;
}
