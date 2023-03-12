package kugods.wonder.app.walk.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IntermediateLocationResponse {

    private Long intermediateLocationId;

    private BigDecimal latitude;

    private BigDecimal longitude;
}
