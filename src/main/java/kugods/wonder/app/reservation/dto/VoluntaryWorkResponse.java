package kugods.wonder.app.reservation.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoluntaryWorkResponse {
    private Long voluntaryWorkId;

    private Long walkId;

    private String specialTheme;

    private String institution;

    private String specificAddress;

    private LocalDate startDate;

    private LocalTime startTime;

    private LocalTime endTime;
}
