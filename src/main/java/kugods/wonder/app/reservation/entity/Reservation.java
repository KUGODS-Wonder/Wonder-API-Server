package kugods.wonder.app.reservation.entity;

import kugods.wonder.app.member.entity.Member;
import kugods.wonder.app.walk.entity.Walk;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Reservation")
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voluntary_work_id")
    private VoluntaryWork voluntaryWork;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isAccepted = false;

}
