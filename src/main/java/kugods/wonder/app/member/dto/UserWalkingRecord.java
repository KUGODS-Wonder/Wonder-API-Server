package kugods.wonder.app.member.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserWalkingRecord {

    public UserWalkingRecord(Long memberId, String name, Double totalDistance){
        this.memberId = memberId;
        this.name = name;
        this.totalDistance = totalDistance;
    }

    private Long memberId;

    private String name;

    private Double totalDistance;

    private Integer ranking;

}
