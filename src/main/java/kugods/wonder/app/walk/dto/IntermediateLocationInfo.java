package kugods.wonder.app.walk.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IntermediateLocationInfo implements Serializable {
    private static final long serialVersionUID = 43567789L;

    private Long intermediateLocationId;

    private BigDecimal latitude;

    private BigDecimal longitude;
}
