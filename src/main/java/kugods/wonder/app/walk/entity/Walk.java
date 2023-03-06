package kugods.wonder.app.walk.entity;

import kugods.wonder.app.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Walk")
@Entity
public class Walk extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "walk_id")
    private Long walkId;

    @Column(length = 45, nullable = false)
    private String title;

    @Column(nullable = false)
    private Double distance;

    @Column(name = "required_time", nullable = false)
    private Integer requiredTime;

    @Column(length = 45, nullable = false)
    private String theme;

    @Column(name = "origin_latitude", precision = 18, scale = 10, nullable = false)
    private BigDecimal originLatitude;

    @Column(name = "origin_longitude", precision = 18, scale = 10, nullable = false)
    private BigDecimal originLongitude;

    @Column(name = "destination_latitude", precision = 18, scale = 10, nullable = false)
    private BigDecimal destinationLatitude;

    @Column(name = "destination_longitude", precision = 18, scale = 10, nullable = false)
    private BigDecimal destinationLongitude;

    @Column(name = "point", nullable = false)
    private Integer point;

    @OneToMany(mappedBy = "walk")
    List<IntermediateLocation> intermediateLocations = new ArrayList<IntermediateLocation>();

}
