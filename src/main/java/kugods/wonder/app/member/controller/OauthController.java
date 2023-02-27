package kugods.wonder.app.member.controller;

import kugods.wonder.app.auth.oauth2.GoogleLoginClientResponse;
import kugods.wonder.app.auth.oauth2.GoogleLoginResponse;
import kugods.wonder.app.auth.oauth2.GoogleLoginUserInformation;
import kugods.wonder.app.auth.oauth2.GoogleOAuthRequest;
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
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;


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

        String reqUrl = googleLoginUrl + "/o/oauth2/v2/auth?client_id=" + googleClientId + "&redirect_uri=" + googleRedirectUrl
                + "&response_type=code&scope=email%20profile%20openid&access_type=offline";
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(reqUrl));

        //1.reqUrl 구글로그인 창을 띄우고, 로그인 후 /login/oauth_google_check 으로 리다이렉션하게 한다.
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    // 구글에서 리다이렉션
    @GetMapping(value = "/login/oauth2/code/google")
    public GoogleLoginClientResponse oauth_google_check(HttpServletRequest request,
                                                  @RequestParam(value = "code") String authCode,
                                                  HttpServletResponse response) throws Exception {

        //2.구글에 등록된 wonder server의 설정정보를 보내어 약속된 토큰을 받위한 객체 생성
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
        ResponseEntity<GoogleLoginResponse> apiResponse = restTemplate.postForEntity(googleAuthUrl + "/token", googleOAuthRequest, GoogleLoginResponse.class);
        //4.받은 토큰을 토큰객체에 저장
        GoogleLoginResponse googleLoginResponse = apiResponse.getBody();

        String googleToken = googleLoginResponse.getId_token();

        //5.받은 토큰을 구글에 보내 유저정보를 얻는다.
        String requestUrl = UriComponentsBuilder.fromHttpUrl(googleAuthUrl + "/tokeninfo").queryParam("id_token", googleToken).toUriString();

        //6.허가된 토큰의 유저정보를 결과로 받는다.
        GoogleLoginUserInformation userInfo = restTemplate.getForObject(requestUrl, GoogleLoginUserInformation.class);

        GoogleLoginClientResponse clientResponse = GoogleLoginClientResponse.builder()
                .googleToken(googleToken)
                .email(userInfo.getEmail())
                .name(userInfo.getName())
                .build();

        return clientResponse;
    }
}
