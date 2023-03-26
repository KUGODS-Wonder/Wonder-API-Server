package kugods.wonder.app.reservation.dto;

import lombok.*;

import java.time.LocalDate;

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
            String description,
            String specificAddress,
            Integer maxPeopleNumber,
            Long currentPeopleNumber,
            boolean active
    ) {
        this.voluntaryWorkId = voluntaryWorkId;
        this.walkId = walkId;
        this.startDate = startDate;
        this.description = description;
        this.specificAddress = specificAddress;
        this.maxPeopleNumber = maxPeopleNumber;
        this.currentPeopleNumber = currentPeopleNumber;
        this.active = active;
    }

    private Long voluntaryWorkId;

    private Long reservationId;

    private Long walkId;

    private LocalDate startDate;

    private String description;

    private String specificAddress;

    private Integer maxPeopleNumber;

    private Long currentPeopleNumber;

    private boolean active;

}
