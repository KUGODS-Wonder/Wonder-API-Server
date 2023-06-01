package kugods.wonder.app.member.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kugods.wonder.app.member.dto.MemberProfileResponse;
import kugods.wonder.app.member.dto.TierInfo;
import kugods.wonder.app.member.dto.UserWalkingRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static kugods.wonder.app.member.entity.QMember.member;
import static kugods.wonder.app.record.entity.QCompletion.completion;
import static kugods.wonder.app.record.entity.QTier.tier;
import static kugods.wonder.app.walk.entity.QWalk.walk;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<UserWalkingRecord> getLocalRanking(MemberProfileResponse profile) {
        return jpaQueryFactory
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
    }

    @Override
    public TierInfo getTierInfo(MemberProfileResponse profile) {
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

    @Override
    public MemberProfileResponse getMemberProfile(String email) {
        return jpaQueryFactory
                .select(Projections.constructor(MemberProfileResponse.class,
                        member.memberId,
                        member.name,
                        member.email,
                        member.address,
                        walk.point.sum().coalesce(0).as("totalPoint"),
                        walk.pathDistance.sum().coalesce(0.0).as("totalDistance"),
                        completion.timeRecord.sum().coalesce(0).as("totalWalkingTime")
                ))
                .from(member)
                .leftJoin(completion).on(member.eq(completion.member))
                .leftJoin(walk).on(walk.eq(completion.walk))
                .where(member.email.eq(email))
                .fetchOne();
    }
}
