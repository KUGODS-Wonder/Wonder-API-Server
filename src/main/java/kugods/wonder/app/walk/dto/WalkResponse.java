package kugods.wonder.app.walk.dto;

import kugods.wonder.app.walk.entity.IntermediateLocation;
import kugods.wonder.app.walk.entity.Tag;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalkResponse {
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
