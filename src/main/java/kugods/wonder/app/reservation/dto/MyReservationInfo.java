package kugods.wonder.app.reservation.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MyReservationInfo implements Serializable {
    private Long voluntaryWorkId;

    private Long reservationId;
}
