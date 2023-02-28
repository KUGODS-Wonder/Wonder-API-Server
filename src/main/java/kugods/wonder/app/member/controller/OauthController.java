package kugods.wonder.app.member.controller;

import kugods.wonder.app.auth.oauth2.dto.GoogleLoginClientResponse;
import kugods.wonder.app.auth.oauth2.dto.GoogleLoginResponse;
import kugods.wonder.app.auth.oauth2.dto.GoogleOAuthRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Objects;

import static kugods.wonder.app.auth.oauth2.GoogleUserInfoProvider.getGoogleLoginClientResponse;


@Slf4j
@RestController
@RequiredArgsConstructor
public class OauthController {

    @Value("${google.auth.url}")
    private String googleAuthUrl;

    @Value("${google.login.url}")
    private String googleLoginUrl;

    @Value("${google.client.id}")
    private String googleClientId;

    @Value("${google.redirect.url}")
    private String googleRedirectUrl;

    @Value("${google.secret}")
    private String googleClientSecret;


    // 구글 로그인창 호출
    @GetMapping(value = "/login/getGoogleAuthUrl")
    public ResponseEntity<?> getGoogleAuthUrl(HttpServletRequest request) throws Exception {
        HttpHeaders headers = setGoogleAuthRequestHeader();
        //1.구글로그인 창을 띄우고, 로그인 후 /login/oauth2/code/google 으로 redirect.
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    // 구글에서 리다이렉션
    @GetMapping(value = "/login/oauth2/code/google")
    public GoogleLoginClientResponse oauth_google_check(
            HttpServletRequest request,
            @RequestParam(value = "code") String authCode,
            HttpServletResponse response
    ) {
        //2.구글에 등록된 wonder server의 설정 정보를 보내고, 약속된 토큰을 받음
        GoogleOAuthRequest googleOAuthRequest = GoogleOAuthRequest
                .builder()
                .clientId(googleClientId)
                .clientSecret(googleClientSecret)
                .code(authCode)
                .redirectUri(googleRedirectUrl)
                .grantType("authorization_code")
                .build();

        RestTemplate restTemplate = new RestTemplate();

        //3.토큰요청을 한다.
        GoogleLoginResponse googleLoginResponse = restTemplate.postForEntity(
                googleAuthUrl + "/token",
                googleOAuthRequest, GoogleLoginResponse.class).getBody();

        //4.받은 토큰을 구글에 보내 유저정보를 얻는다.
        return getGoogleLoginClientResponse(googleAuthUrl, Objects.requireNonNull(googleLoginResponse).getId_token());
    }

    private HttpHeaders setGoogleAuthRequestHeader() {
        String reqUrl = setRequestUrl();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(reqUrl));
        return headers;
    }

    private String setRequestUrl() {
        return googleLoginUrl + "/o/oauth2/v2/auth?client_id=" + googleClientId + "&redirect_uri=" + googleRedirectUrl
                + "&response_type=code&scope=email%20profile%20openid&access_type=offline";
    }
}
