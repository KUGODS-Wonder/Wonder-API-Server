package kugods.wonder.app.reservation.controller;

import kugods.wonder.app.common.dto.ApiDataResponse;
import kugods.wonder.app.reservation.dto.VoluntaryWorkResponse;
import kugods.wonder.app.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
