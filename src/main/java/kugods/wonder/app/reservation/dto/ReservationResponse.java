package kugods.wonder.app.reservation.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {

    private Long voluntaryWorkId;

    private Long walkId;

    private LocalDate startDate;

    private String description;

    private String specificAddress;

    private Integer maxPeopleNumber;

    private Long currentPeopleNumber;

    private boolean active;

}
