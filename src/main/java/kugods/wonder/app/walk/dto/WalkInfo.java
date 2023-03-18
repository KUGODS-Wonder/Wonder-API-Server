package kugods.wonder.app.walk.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalkInfo {
    public WalkInfo(
            Long walkId, String title, Double distance, Integer requiredTime, String theme, BigDecimal originLatitude,BigDecimal originLongitude,BigDecimal destinationLatitude,BigDecimal destinationLongitude,Integer point,Double boundary
    ) {
        this.walkId = walkId;
        this.title = title;
        this.distance = distance;
        this.requiredTime = requiredTime;
        this.theme = theme;
        this.originLatitude = originLatitude;
        this.originLongitude = originLongitude;
        this.destinationLatitude =destinationLatitude;
        this.destinationLongitude = destinationLongitude;
        this.point = point;
        this.boundary = boundary;
    }
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

    private List<IntermediateLocationInfo> intermediateLocationList;

    private List<TagInfo> tagList;
}

