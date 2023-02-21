package kugods.wonder.app.user.entity;

import kugods.wonder.app.common.entity.BaseEntity;
import kugods.wonder.app.record.entity.Tier;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(name = "User")
@Entity
public class Member extends BaseEntity {

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

    protected Member() {}

    protected Member(String name, String address, String refreshToken, String email, String password, Tier tier){
        this.name = name;
        this.address = address;
        this.refreshToken = refreshToken;
        this.email = email;
        this.password = password;
        this.tier = tier;
    }

    public static Member of(String name, String address, String refreshToken, String email, String password, Tier tier) {
        return new Member(name, address, refreshToken, email,password, tier);
    }
}
