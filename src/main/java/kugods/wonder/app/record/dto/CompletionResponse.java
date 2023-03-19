package kugods.wonder.app.record.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompletionResponse {

    private Long completionId;

    private Long walkId;

    private Long memberId;

    private Integer timeRecord;

}
