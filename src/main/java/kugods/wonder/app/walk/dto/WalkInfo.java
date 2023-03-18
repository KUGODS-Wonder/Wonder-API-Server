package kugods.wonder.app.walk.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalkInfo {
    private Long walkId;

    private String title;

    private Double distance;

    private Integer requiredTime;

    private String theme;

    private BigDecimal originLatitude;

    private BigDecimal originLongitude;

    private BigDecimal destinationLatitude;

    private BigDecimal destinationLongitude;

    private Integer point;

    private Double boundary;
}

