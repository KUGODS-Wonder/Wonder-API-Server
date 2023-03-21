package kugods.wonder.app.member.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberProfileResponse {

    public MemberProfileResponse(
            Long memberId,
            String name,
            String email,
            String address,
            Integer totalPoint,
            Double totalDistance,
            Integer totalWalkingTime
    ) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.totalPoint = totalPoint;
        this.totalDistance = totalDistance;
        this.totalWalkingTime = totalWalkingTime;
    }

    private Long memberId;

    private String name;

    private String email;

    private String address;

    private TierInfo tierInfo;

    private Integer totalPoint;

    private Double totalDistance;

    private Integer totalWalkingTime;

    private Integer myRanking;

    private List<UserWalkingRecord> localRankingTopFive;

    // To do: 메달 리스트
}
