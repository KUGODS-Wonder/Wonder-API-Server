package kugods.wonder.app.reservation.repository;

import kugods.wonder.app.reservation.dto.MyReservationInfo;
import kugods.wonder.app.reservation.dto.ReservationResponse;

import java.util.List;

public interface ReservationCustomRepository {

    List<ReservationResponse> getReservationList();

    List<MyReservationInfo> getMyReservations(String email);
}
