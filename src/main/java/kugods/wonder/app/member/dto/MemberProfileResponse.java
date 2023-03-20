package kugods.wonder.app.member.dto;

import kugods.wonder.app.record.entity.Tier;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberProfileResponse {

    private Long memberId;

    private String name;

    private String email;

    private Tier tier;

    private Integer totalPoint;

    private Integer totalWalkingTime;

    private Double totalDistance;

    private String address;

    private List<UserWalkingRecord> localRanking;

    // To do: 메달 리스트
}
