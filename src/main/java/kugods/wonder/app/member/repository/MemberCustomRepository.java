package kugods.wonder.app.member.repository;

import kugods.wonder.app.member.dto.MemberProfileResponse;
import kugods.wonder.app.member.dto.TierInfo;
import kugods.wonder.app.member.dto.UserWalkingRecord;

import java.util.List;

public interface MemberCustomRepository {

    List<UserWalkingRecord> getLocalRanking(MemberProfileResponse profile);

    TierInfo getTierInfo(MemberProfileResponse profile);

    MemberProfileResponse getMemberProfile(String email);

}
