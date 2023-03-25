package kugods.wonder.app.reservation.service;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kugods.wonder.app.reservation.dto.ReservationResponse;
import kugods.wonder.app.reservation.dto.VoluntaryWorkResponse;
import kugods.wonder.app.reservation.entity.VoluntaryWork;
import kugods.wonder.app.reservation.repository.ReservationRepository;
import kugods.wonder.app.reservation.repository.VoluntaryWorkRepository;
import kugods.wonder.app.walk.repository.WalkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.querydsl.core.types.ExpressionUtils.count;
import static kugods.wonder.app.reservation.entity.QReservation.reservation;
import static kugods.wonder.app.reservation.entity.QVoluntaryWork.voluntaryWork;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final JPAQueryFactory jpaQueryFactory;
    private final ReservationRepository reservationRepository;
    private final WalkRepository walkRepository;
    private final VoluntaryWorkRepository voluntaryWorkRepository;

    @Override
    public List<VoluntaryWorkResponse> getVoluntaryWorkList() {
        return voluntaryWorkRepository.findAll()
                .stream()
                .filter(VoluntaryWork::isActive)
                .map(VoluntaryWork::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationResponse> getReservationList(String email) {
        List<ReservationResponse> reservationList = jpaQueryFactory
                .select(Projections.constructor(ReservationResponse.class,
                        voluntaryWork.voluntaryWorkId,
                        voluntaryWork.walk.walkId,
                        voluntaryWork.startDate,
                        voluntaryWork.description,
                        voluntaryWork.specificAddress,
                        voluntaryWork.maxPeopleNumber,
                        count(reservation.reservationId),
                        voluntaryWork.active
                ))
                .from(voluntaryWork)
                .join(reservation).on(voluntaryWork.eq(reservation.voluntaryWork))
                .groupBy(voluntaryWork.voluntaryWorkId)
                .fetch();

        List<Long> myReservations = jpaQueryFactory.
                select(voluntaryWork.voluntaryWorkId)
                .from(voluntaryWork)
                .join(reservation).on(voluntaryWork.eq(reservation.voluntaryWork))
                .where(reservation.member.email.eq(email))
                .fetch();

        return reservationList.stream()
                .filter(reservationInfo -> myReservations.contains(reservationInfo.getVoluntaryWorkId()))
                .collect(Collectors.toList());
    }
}
