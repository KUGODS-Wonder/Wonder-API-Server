package kugods.wonder.app.member.service;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kugods.wonder.app.member.dto.MemberProfileResponse;
import kugods.wonder.app.member.dto.TierInfo;
import kugods.wonder.app.member.dto.UserWalkingRecord;
import kugods.wonder.app.member.repository.MemberRepository;
import kugods.wonder.app.walk.dto.WalkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kugods.wonder.app.member.entity.QMember.member;
import static kugods.wonder.app.record.entity.QCompletion.completion;
import static kugods.wonder.app.record.entity.QTier.tier;
import static kugods.wonder.app.walk.entity.QWalk.walk;

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

        return getMemberProfileResponse(profile, tierInfo, localRanking);
    }

    private MemberProfileResponse getMemberProfileResponse(MemberProfileResponse profile, TierInfo tierInfo, List<UserWalkingRecord> localRanking) {
        profile.setTierInfo(tierInfo);
        profile.setLocalRanking(localRanking);
        return profile;
    }

    private List<UserWalkingRecord> getMemberLocalRanking(MemberProfileResponse profile) {
        List<UserWalkingRecord> localRanking = jpaQueryFactory
                .select(Projections.constructor(UserWalkingRecord.class,
                        member.memberId,
                        member.name,
                        walk.pathDistance.sum().as("totalDistance")
                ))
                .from(member)
                .leftJoin(completion).on(member.eq(completion.member))
                .leftJoin(walk).on(walk.eq(completion.walk))
                .where(walk.address.eq(profile.getAddress()))
                .fetch();
        return localRanking;
    }

    private TierInfo getTierInfo(MemberProfileResponse profile) {
        return jpaQueryFactory
                .select(Projections.constructor(TierInfo.class,
                        tier.name,
                        tier.minPointToUpgrade
                ))
                .from(tier)
                .where(tier.minPointToUpgrade.loe(profile.getTotalPoint()))
                .orderBy(tier.minPointToUpgrade.desc())
                .limit(1)
                .fetchOne();
    }

    private MemberProfileResponse getProfileByEmail(String email) {
        MemberProfileResponse profile = jpaQueryFactory
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
        return profile;
    }
}
