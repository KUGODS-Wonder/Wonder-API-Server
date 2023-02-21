package kugods.wonder.app.record.entity;

import kugods.wonder.app.common.entity.BaseEntity;
import kugods.wonder.app.user.entity.Member;
import kugods.wonder.app.walk.entity.Walk;

import javax.persistence.*;

@Table(name = "Completion")
@Entity
public class Completion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "completion_id")
    private Long completionId;

    @Column(name = "spend_time")
    private int spendTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "walk_id")
    private Walk walk;
}
