package kugods.wonder.app.walk.entity;

import kugods.wonder.app.common.entity.BaseEntity;
import kugods.wonder.app.walk.dto.IntermediateLocationInfo;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "IntermediateLocation")
@Entity
public class IntermediateLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long intermediateLocationId;

    @Column(precision = 18, scale = 10, nullable = false)
    private BigDecimal latitude;

    @Column(precision = 18, scale = 10, nullable = false)
    private BigDecimal longitude;

    @ManyToOne
    @JoinColumn(name = "walk_id")
    private Walk walk;

    public IntermediateLocationInfo toResponse() {
        return IntermediateLocationInfo.builder()
                .intermediateLocationId(intermediateLocationId)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
