package kugods.wonder.app.auth.service;

import kugods.wonder.app.auth.domain.jwt.TokenProvider;
import kugods.wonder.app.auth.domain.custom.CustomPasswordEncoder;
import kugods.wonder.app.auth.dto.*;
import kugods.wonder.app.member.entity.Authority;
import kugods.wonder.app.member.entity.Member;
import kugods.wonder.app.auth.exception.DuplicatedEmailException;
import kugods.wonder.app.auth.exception.InvalidGoogleToken;
import kugods.wonder.app.auth.exception.InvalidPasswordException;
import kugods.wonder.app.auth.exception.MemberDoesNotExistException;
import kugods.wonder.app.member.repository.MemberRepository;
import kugods.wonder.app.record.repository.TierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static kugods.wonder.app.auth.domain.oauth2.GoogleUserInfoProvider.getGoogleLoginClientResponse;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final TierRepository tierRepository;
    private final TokenProvider tokenProvider;

    @Value("${google.auth.url}")
    private String googleAuthUrl;
    private final String DEFAULT_OAUTH_GOOGLE_PASSWORD = "GOOGLE";

    public SigninResponse signin(SigninRequest request) {
        Member member = memberRepository.findOneByEmail(request.getEmail())
                .orElseThrow(MemberDoesNotExistException::new);
        validatePassword(member, request.getPassword());

        return SigninResponse.builder()
                .memberId(member.getMemberId())
                .token(tokenProvider.createToken(member.getEmail(), member.getRoles()))
                .build();
    }

    public SignupResponse signup(SignupRequest request) {
        //중복확인
        validateEmailDuplication(request.getEmail());
        
        Member member = Member.builder()
                .email(request.getEmail())
                .password(CustomPasswordEncoder.hashPassword(request.getPassword()))
                .name(request.getName())
                .address(request.getAddress())
                .tier(tierRepository.findById(1L).orElseThrow()) // 브론즈5에서 시작.
                .build();
        member.setRoles(Collections.singletonList(Authority.builder().name("ROLE_USER").build()));
        memberRepository.save(member);

        return SignupResponse.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .name(member.getName())
                .address(member.getAddress())
                .token(tokenProvider.createToken(member.getEmail(), member.getRoles()))
                .build();
    }
    
    public SigninResponse googleLogin(OauthLoginReqeust request, String googleToken) {
        validateGoogleToken(googleToken); // jwt Filter가 아닌, google 발급 유효성 검증만 진행
        return googleLoginResponse(request, memberRepository.findOneByEmail(request.getEmail()).isPresent());
    }

    private SigninResponse googleLoginResponse(OauthLoginReqeust request, boolean whetherSignUp) {
        if (whetherSignUp) {
            return googleLoginWithoutSignUp(request);
        } else {
            return googleLoginWithSignUp(request);
        }
    }

    private SigninResponse googleLoginWithSignUp(OauthLoginReqeust request) {
        Member member = Member.builder()
                .email(request.getEmail())
                .password(DEFAULT_OAUTH_GOOGLE_PASSWORD) // 소셜 로그인
                .name(request.getName())
                .address(request.getAddress())
                .tier(tierRepository.findById(1L).orElseThrow()) // 브론즈5에서 시작.
                .build();
        member.setRoles(Collections.singletonList(Authority.builder().name("ROLE_USER").build()));
        memberRepository.save(member);

        return SigninResponse.builder()
                .memberId(member.getMemberId())
                .token(tokenProvider.createToken(member.getEmail(), member.getRoles()))
                .build();
    }

    private SigninResponse googleLoginWithoutSignUp(OauthLoginReqeust request) {
        Member member = memberRepository.findOneByEmail(request.getEmail())
                .orElseThrow(MemberDoesNotExistException::new);

        return SigninResponse.builder()
                .memberId(member.getMemberId())
                .token(tokenProvider.createToken(member.getEmail(), member.getRoles()))
                .build();
    }

    private void validateGoogleToken(String googleToken) {
        try {
            getGoogleLoginClientResponse(googleAuthUrl, googleToken);
        } catch (Exception e) {
            throw new InvalidGoogleToken();
        }
    }

    private void validatePassword(Member member, String password) {
        boolean isMatched = CustomPasswordEncoder.isMatched(
                password,
                member.getPassword()
        );
        if (!isMatched) {
            throw new InvalidPasswordException();
        }
    }

    private void validateEmailDuplication(String email) {
        // 이메일 검증
        boolean isDuplicated = memberRepository.findOneByEmail(email).isPresent();
        if (isDuplicated) {
            throw new DuplicatedEmailException();
        }
    }
}
