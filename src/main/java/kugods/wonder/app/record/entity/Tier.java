package kugods.wonder.app.record.entity;

import kugods.wonder.app.common.entity.BaseEntity;

import javax.persistence.*;

@Entity
public class Tier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tier_id")
    private Long tierId;

    @Column(nullable = false)
    private String name;

    @Column(name = "min_point_to_upgrade", nullable = false)
    private int minPointToUpgrade;

}
