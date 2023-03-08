package kugods.wonder.app.auth.domain.oauth2;

import kugods.wonder.app.auth.domain.oauth2.dto.GoogleLoginClientResponse;
import kugods.wonder.app.auth.domain.oauth2.dto.GoogleLoginUserInformation;
import kugods.wonder.app.member.entity.Member;
import kugods.wonder.app.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class GoogleUserInfoProvider {

    private final MemberRepository memberRepository;
    private final String NOT_MEMBER_ADDRESS = "unknown";

    public GoogleLoginClientResponse getGoogleLoginClientResponse(String googleAuthUrl, String googleToken) {
        RestTemplate restTemplate = new RestTemplate();
        String requestUrl = UriComponentsBuilder.fromHttpUrl(googleAuthUrl + "/tokeninfo").queryParam("id_token", googleToken).toUriString();

        GoogleLoginUserInformation userInfo = restTemplate.getForObject(requestUrl, GoogleLoginUserInformation.class);

        return checkRegisteredAndReturnResponse(googleToken, userInfo);
    }

    private GoogleLoginClientResponse checkRegisteredAndReturnResponse(String googleToken, GoogleLoginUserInformation userInfo) {
        Optional<Member> member = memberRepository.findOneByEmail(userInfo.getEmail());

        if (member.isPresent()) {
            return GoogleLoginClientResponse.builder()
                    .googleToken(googleToken)
                    .email(userInfo.getEmail())
                    .name(userInfo.getName())
                    .address(member.get().getAddress())
                    .alreadyRegistered(true)
                    .build();
        }

        return GoogleLoginClientResponse.builder()
                .googleToken(googleToken)
                .email(userInfo.getEmail())
                .name(userInfo.getName())
                .address(NOT_MEMBER_ADDRESS)
                .alreadyRegistered(false)
                .build();
    }
}
