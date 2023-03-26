package kugods.wonder.app.reservation.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyReservationInfo {
    private Long voluntaryWorkId;

    private Long reservationId;
}
