package kugods.wonder.app.user.entity;

import kugods.wonder.app.common.entity.BaseEntity;
import kugods.wonder.app.record.entity.Tier;

import javax.persistence.*;

@Table(name = "User")
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;


    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "tier_id")
    private Tier tier;
}
