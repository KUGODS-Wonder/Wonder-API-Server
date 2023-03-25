package kugods.wonder.app.reservation.service;

import kugods.wonder.app.reservation.dto.MakeReservationsResponse;
import kugods.wonder.app.reservation.dto.ReservationRequest;
import kugods.wonder.app.reservation.dto.ReservationResponse;
import kugods.wonder.app.reservation.dto.VoluntaryWorkResponse;

import java.util.List;

public interface ReservationService {
    List<VoluntaryWorkResponse> getVoluntaryWorkList();

    List<ReservationResponse> getReservationList(String email);

    MakeReservationsResponse makeReservations(ReservationRequest request);

    MakeReservationsResponse cancelReservations(Long reservationId);
}
