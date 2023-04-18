package kugods.wonder.app.reservation.service;

import kugods.wonder.app.member.entity.Member;
import kugods.wonder.app.member.exception.MemberDoesNotExistException;
import kugods.wonder.app.member.repository.MemberRepository;
import kugods.wonder.app.reservation.dto.*;
import kugods.wonder.app.reservation.entity.Reservation;
import kugods.wonder.app.reservation.entity.VoluntaryWork;
import kugods.wonder.app.reservation.exception.DuplicatedReservationException;
import kugods.wonder.app.reservation.exception.ReservationDoesNotExistException;
import kugods.wonder.app.reservation.exception.VoluntaryWorkDoesNotExistException;
import kugods.wonder.app.reservation.repository.ReservationCustomRepository;
import kugods.wonder.app.reservation.repository.ReservationRepository;
import kugods.wonder.app.reservation.repository.VoluntaryWorkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationCustomRepository reservationCustomRepository;
    private final VoluntaryWorkRepository voluntaryWorkRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public List<VoluntaryWorkResponse> getVoluntaryWorkList() {
        return voluntaryWorkRepository.findAll()
                .stream()
                .filter(VoluntaryWork::isActive)
                .map(VoluntaryWork::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> getReservationList(String email) {
        List<ReservationResponse> reservationList = getReservationList();
        List<MyReservationInfo> myReservations = getMyReservations(email);

        List<Long> voluntaryWorkIdList = myReservations.stream()
                .map(MyReservationInfo::getVoluntaryWorkId)
                .collect(Collectors.toList());

        return reservationList.stream()
                .filter(reservationInfo -> voluntaryWorkIdList.contains(reservationInfo.getVoluntaryWorkId()))
                .peek(reservationInfo -> reservationInfo.setReservationId(
                        myReservations.stream()
                                .filter(myReservation -> myReservation.getVoluntaryWorkId().equals(reservationInfo.getVoluntaryWorkId()))
                                .map(MyReservationInfo::getReservationId)
                                .collect(Collectors.toList()).get(0)
                ))
                .collect(Collectors.toList());
    }

    private List<ReservationResponse> getReservationList() {
        return reservationCustomRepository.getReservationList();
    }

    private List<MyReservationInfo> getMyReservations(String email) {
        return reservationCustomRepository.getMyReservations(email);
    }

    @Override
    @Transactional
    public MakeReservationsResponse makeReservations(ReservationRequest request) {
        validateReservationDuplication(request.getEmail(), request.getVoluntaryWorkId());
        Reservation reservation = getReservation(request);
        reservationRepository.save(reservation);

        return reservation.toResponse();
    }

    private Reservation getReservation(ReservationRequest request) {
        Member member = memberRepository.findOneByEmail(request.getEmail())
                .orElseThrow(MemberDoesNotExistException::new);
        VoluntaryWork voluntaryWork = voluntaryWorkRepository.findById(request.getVoluntaryWorkId())
                .orElseThrow(VoluntaryWorkDoesNotExistException::new);
        return Reservation.builder()
                .member(member)
                .voluntaryWork(voluntaryWork)
                .build();
    }

    @Override
    @Transactional
    public MakeReservationsResponse cancelReservations(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(ReservationDoesNotExistException::new);
        reservationRepository.delete(reservation);

        return reservation.toResponse();
    }

    private void validateReservationDuplication(String email, Long voluntaryWorkId) {
        boolean isDuplicated = reservationRepository.findOneByMember_EmailAndVoluntaryWork_VoluntaryWorkId(email, voluntaryWorkId).isPresent();
        if (isDuplicated) {
            throw new DuplicatedReservationException();
        }
    }
}
