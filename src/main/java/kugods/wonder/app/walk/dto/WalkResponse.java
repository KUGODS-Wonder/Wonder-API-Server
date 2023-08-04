package kugods.wonder.app.walk.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WalkResponse implements Serializable {
    private static final long serialVersionUID = 1761789L;
    public WalkResponse(
            Long walkId,
            String title,
            Double pathDistance,
            Integer requiredTime,
            String address,
            String theme,
            BigDecimal originLatitude,
            BigDecimal originLongitude,
            BigDecimal destinationLatitude,
            BigDecimal destinationLongitude,
            Integer point,
            Double distanceToUser
    ) {
        this.walkId = walkId;
        this.title = title;
        this.pathDistance = pathDistance;
        this.requiredTime = requiredTime;
        this.address = address;
        this.theme = theme;
        this.originLatitude = originLatitude;
        this.originLongitude = originLongitude;
        this.destinationLatitude =destinationLatitude;
        this.destinationLongitude = destinationLongitude;
        this.point = point;
        this.distanceToUser = distanceToUser;
    }
    private Long walkId;

    private String title;

    private Double pathDistance;

    private Integer requiredTime;

    private String address;

    private String theme;

    private BigDecimal originLatitude;

    private BigDecimal originLongitude;

    private BigDecimal destinationLatitude;

    private BigDecimal destinationLongitude;

    private Integer point;

    private Double distanceToUser;

    private List<IntermediateLocationInfo> intermediateLocationList;

    private List<TagInfo> tagList;
}

