package kugods.wonder.app.member.service;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kugods.wonder.app.common.constant.ErrorCode;
import kugods.wonder.app.common.exception.GeneralException;
import kugods.wonder.app.member.dto.MemberProfileResponse;
import kugods.wonder.app.member.dto.TierInfo;
import kugods.wonder.app.member.dto.UserWalkingRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static kugods.wonder.app.member.entity.QMember.member;
import static kugods.wonder.app.record.entity.QCompletion.completion;
import static kugods.wonder.app.record.entity.QTier.tier;
import static kugods.wonder.app.walk.entity.QWalk.walk;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final JPAQueryFactory jpaQueryFactory;

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
        List<UserWalkingRecord> localRanking = jpaQueryFactory
                .select(Projections.constructor(UserWalkingRecord.class,
                        member.memberId,
                        member.name,
                        walk.pathDistance.sum().coalesce(0.0).as("totalDistance")
                ))
                .from(member)
                .leftJoin(completion).on(member.eq(completion.member))
                .leftJoin(walk).on(walk.eq(completion.walk))
                .where(member.address.eq(profile.getAddress()))
                .groupBy(member.memberId)
                .orderBy(Expressions.numberPath(Double.class, "totalDistance").desc())
                .fetch();

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
        return jpaQueryFactory
                .select(Projections.constructor(TierInfo.class,
                        tier.name,
                        tier.minPointToUpgrade
                ))
                .from(tier)
                .where(tier.minPointToUpgrade.goe(profile.getTotalPoint()))
                .orderBy(tier.minPointToUpgrade.asc())
                .limit(1)
                .fetchOne();
    }

    private MemberProfileResponse getProfileByEmail(String email) {
        return jpaQueryFactory
                .select(Projections.constructor(MemberProfileResponse.class,
                        member.memberId,
                        member.name,
                        member.email,
                        member.address,
                        walk.point.sum().as("totalPoint"),
                        walk.pathDistance.sum().as("totalDistance"),
                        completion.timeRecord.sum().as("totalWalkingTime")
                ))
                .from(member)
                .leftJoin(completion).on(member.eq(completion.member))
                .leftJoin(walk).on(walk.eq(completion.walk))
                .where(member.email.eq(email))
                .fetchOne();
    }
}
