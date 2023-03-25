package kugods.wonder.app.reservation.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MakeReservationsResponse {
    private Long reservationId;

    private Long voluntaryWorkId;
}
