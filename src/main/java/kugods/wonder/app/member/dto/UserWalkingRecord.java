package kugods.wonder.app.member.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserWalkingRecord {

    private Long memberId;

    private String name;

    private Double totalDistance;

}
