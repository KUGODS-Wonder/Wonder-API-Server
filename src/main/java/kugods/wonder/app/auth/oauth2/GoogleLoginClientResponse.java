package kugods.wonder.app.auth.oauth2;

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
}
