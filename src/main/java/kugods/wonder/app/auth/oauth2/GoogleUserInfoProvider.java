package kugods.wonder.app.auth.oauth2;

import kugods.wonder.app.auth.oauth2.dto.GoogleLoginClientResponse;
import kugods.wonder.app.auth.oauth2.dto.GoogleLoginUserInformation;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class GoogleUserInfoProvider {

    public static GoogleLoginClientResponse getGoogleLoginClientResponse(String googleAuthUrl, String googleToken) {
        RestTemplate restTemplate = new RestTemplate();
        String requestUrl = UriComponentsBuilder.fromHttpUrl(googleAuthUrl + "/tokeninfo").queryParam("id_token", googleToken).toUriString();

        GoogleLoginUserInformation userInfo = restTemplate.getForObject(requestUrl, GoogleLoginUserInformation.class);

        GoogleLoginClientResponse clientResponse = GoogleLoginClientResponse.builder()
                .googleToken(googleToken)
                .email(userInfo.getEmail())
                .name(userInfo.getName())
                .build();
        return clientResponse;
    }
}
