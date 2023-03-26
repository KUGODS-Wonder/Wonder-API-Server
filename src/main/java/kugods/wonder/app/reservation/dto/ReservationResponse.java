package kugods.wonder.app.reservation.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {

    public ReservationResponse(
            Long voluntaryWorkId,
            Long walkId,
            LocalDate startDate,
            LocalTime startTime,
            LocalTime endTime,
            String specificAddress,
            Integer maxPeopleNumber,
            Long currentPeopleNumber,
            boolean active
    ) {
        this.voluntaryWorkId = voluntaryWorkId;
        this.walkId = walkId;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.specificAddress = specificAddress;
        this.maxPeopleNumber = maxPeopleNumber;
        this.currentPeopleNumber = currentPeopleNumber;
        this.active = active;
    }

    private Long voluntaryWorkId;

    private Long reservationId;

    private Long walkId;

    private LocalDate startDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private String specificAddress;

    private Integer maxPeopleNumber;

    private Long currentPeopleNumber;

    private boolean active;

}
