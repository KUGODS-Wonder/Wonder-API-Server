package kugods.wonder.app.member.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TierInfo {

    private String tierName;

    private Integer minPointToUpgrade;
}
