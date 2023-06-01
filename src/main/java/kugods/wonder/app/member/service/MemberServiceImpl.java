package kugods.wonder.app.member.service;

import kugods.wonder.app.common.constant.ErrorCode;
import kugods.wonder.app.common.exception.GeneralException;
import kugods.wonder.app.member.dto.MemberProfileResponse;
import kugods.wonder.app.member.dto.TierInfo;
import kugods.wonder.app.member.dto.UserWalkingRecord;
import kugods.wonder.app.member.repository.MemberCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberCustomRepository memberCustomRepository;

    @Override
    @Transactional(readOnly = true)
    public MemberProfileResponse getProfile(String email) {
        MemberProfileResponse profile = getProfileByEmail(email);
        TierInfo tierInfo = getTierInfo(profile);
        List<UserWalkingRecord> localRanking = getMemberLocalRanking(profile);
        Integer myRanking = getMyRanking(profile, localRanking);

        return getMemberProfileResponse(profile, tierInfo, myRanking, localRanking);
    }

    private MemberProfileResponse getMemberProfileResponse(
            MemberProfileResponse profile,
            TierInfo tierInfo,
            Integer myRanking,
            List<UserWalkingRecord> localRanking
    ) {
        profile.setTierInfo(tierInfo);
        profile.setMyRanking(myRanking);
        profile.setLocalRankingTopFive(localRanking.stream() // myRanking을 구하기 위해 위에서 전체 쿼리 후 여기서 limit.
                .limit(5)
                .collect(Collectors.toList()));

        return profile;
    }

    private List<UserWalkingRecord> getMemberLocalRanking(MemberProfileResponse profile) {
        List<UserWalkingRecord> localRanking = memberCustomRepository.getLocalRanking(profile);

        return localRanking.stream()
                .map(record -> new UserWalkingRecord(
                        record.getMemberId(),
                        record.getName(),
                        record.getTotalDistance(),
                        localRanking.indexOf(record) + 1))
                .collect(Collectors.toList());
    }

    private Integer getMyRanking(MemberProfileResponse profile, List<UserWalkingRecord> localRanking) {
        return localRanking.stream()
                .filter(r -> r.getMemberId().equals(profile.getMemberId()))
                .findFirst()
                .map(UserWalkingRecord::getRanking)
                .orElseThrow(() -> new GeneralException(ErrorCode.DATA_ACCESS_ERROR));
    }

    private TierInfo getTierInfo(MemberProfileResponse profile) {
        return memberCustomRepository.getTierInfo(profile);
    }

    private MemberProfileResponse getProfileByEmail(String email) {
        return memberCustomRepository.getMemberProfile(email);
    }
}
