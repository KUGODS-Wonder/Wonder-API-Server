package kugods.wonder.app.walk.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WalkListRequest {
    private BigDecimal latitude;

    private BigDecimal longitude;

    private Double range;
}
