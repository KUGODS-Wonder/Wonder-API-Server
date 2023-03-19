package kugods.wonder.app.record.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompletionRequest {

    private Long walkId;

    private Integer timeRecord;

}
