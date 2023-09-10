package kugods.wonder.app.reservation.controller;

import kugods.wonder.app.common.dto.ApiDataResponse;
import kugods.wonder.app.reservation.dto.MakeReservationsResponse;
import kugods.wonder.app.reservation.dto.ReservationRequest;
import kugods.wonder.app.reservation.dto.ReservationResponse;
import kugods.wonder.app.reservation.dto.VoluntaryWorkResponse;
import kugods.wonder.app.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ApiDataResponse<List<VoluntaryWorkResponse>> getReservations() {
        return ApiDataResponse.of(reservationService.getVoluntaryWorkList());
    }

    @GetMapping("/mine")
    public ApiDataResponse<List<ReservationResponse>> getMyReservations(
            Authentication auth
    ) {
        return ApiDataResponse.of(reservationService.getReservationList(auth.getName()));
    }

    @PostMapping("/{voluntaryWorkId}")
    public ApiDataResponse<MakeReservationsResponse> makeReservations(
            Authentication auth,
            @PathVariable("voluntaryWorkId") Long voluntaryWorkId
    ) {
        return ApiDataResponse.of(
                reservationService.makeReservations(ReservationRequest.builder()
                        .email(auth.getName())
                        .voluntaryWorkId(voluntaryWorkId)
                        .build())
        );
    }

    @DeleteMapping("/cancel/{reservationId}")
    public ApiDataResponse<MakeReservationsResponse> cancelReservations(
            Authentication auth,
            @PathVariable("reservationId") Long reservationId
    ) {
        String email = auth.getName();
        return ApiDataResponse.of(
                reservationService.cancelReservations(reservationId, email)
        );
    }
}
