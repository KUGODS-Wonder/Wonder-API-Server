package kugods.wonder.app.auth.domain.oauth2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleLoginClientResponse {
    private String googleToken;
    private String email;
    private String name;
    private String address;
    private boolean alreadyRegistered;
}
