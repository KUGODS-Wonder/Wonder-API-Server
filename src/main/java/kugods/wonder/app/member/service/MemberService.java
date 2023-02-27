package kugods.wonder.app.member.service;

import kugods.wonder.app.auth.jwt.TokenProvider;
import kugods.wonder.app.auth.custom.CustomPasswordEncoder;
import kugods.wonder.app.member.dto.SigninRequest;
import kugods.wonder.app.member.dto.SigninResponse;
import kugods.wonder.app.member.dto.SignupRequest;
import kugods.wonder.app.member.dto.SignupResponse;
import kugods.wonder.app.member.entity.Authority;
import kugods.wonder.app.member.entity.Member;
import kugods.wonder.app.member.exception.DuplicatedEmailException;
import kugods.wonder.app.member.exception.InvalidPasswordException;
import kugods.wonder.app.member.exception.MemberDoesNotExistException;
import kugods.wonder.app.member.repository.MemberRepository;
import kugods.wonder.app.record.repository.TierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TierRepository tierRepository;
    private final TokenProvider tokenProvider;

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
