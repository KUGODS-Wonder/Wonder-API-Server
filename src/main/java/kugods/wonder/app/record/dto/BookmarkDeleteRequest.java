package kugods.wonder.app.record.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkDeleteRequest {

    @NotNull
    private Long memberId;

    @NotNull
    private Long walkId;
}
