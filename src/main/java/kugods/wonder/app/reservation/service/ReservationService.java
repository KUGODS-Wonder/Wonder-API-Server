package kugods.wonder.app.reservation.service;

import kugods.wonder.app.reservation.dto.ReservationResponse;
import kugods.wonder.app.reservation.dto.VoluntaryWorkResponse;

import java.util.List;

public interface ReservationService {
    List<VoluntaryWorkResponse> getVoluntaryWorkList();

    List<ReservationResponse> getReservationList(String email);
}
