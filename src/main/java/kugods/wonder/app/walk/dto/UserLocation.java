package kugods.wonder.app.walk.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLocation {

    private BigDecimal latitude;

    private BigDecimal longitude;

    public static UserLocation to(BigDecimal latitude, BigDecimal longitude) {
        return UserLocation.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
