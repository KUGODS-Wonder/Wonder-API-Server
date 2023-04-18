package kugods.wonder.app.reservation.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kugods.wonder.app.reservation.dto.MyReservationInfo;
import kugods.wonder.app.reservation.dto.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.core.types.ExpressionUtils.count;
import static kugods.wonder.app.reservation.entity.QReservation.reservation;
import static kugods.wonder.app.reservation.entity.QVoluntaryWork.voluntaryWork;


@Repository
@RequiredArgsConstructor
public class ReservationCustomRepositoryImpl implements ReservationCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ReservationResponse> getReservationList() {
        return jpaQueryFactory
                .select(Projections.constructor(ReservationResponse.class,
                        voluntaryWork.voluntaryWorkId,
                        voluntaryWork.walk.walkId,
                        voluntaryWork.startDate,
                        voluntaryWork.startTime,
                        voluntaryWork.endTime,
                        voluntaryWork.specificAddress,
                        voluntaryWork.maxPeopleNumber,
                        count(reservation.reservationId),
                        voluntaryWork.active
                ))
                .from(voluntaryWork)
                .join(reservation).on(voluntaryWork.eq(reservation.voluntaryWork))
                .groupBy(voluntaryWork.voluntaryWorkId)
                .fetch();
    }

    @Override
    public List<MyReservationInfo> getMyReservations(String email) {
        return jpaQueryFactory.
                select(Projections.constructor(MyReservationInfo.class,
                        voluntaryWork.voluntaryWorkId,
                        reservation.reservationId))
                .from(voluntaryWork)
                .join(reservation).on(voluntaryWork.eq(reservation.voluntaryWork))
                .where(reservation.member.email.eq(email))
                .fetch();
    }
}
