package kugods.wonder.app.reservation.dto;

import lombok.*;

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
}
