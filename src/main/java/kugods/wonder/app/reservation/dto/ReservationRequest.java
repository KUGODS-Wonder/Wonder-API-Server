package kugods.wonder.app.reservation.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {
    private Long voluntaryWorkId;

    private String email;
}
