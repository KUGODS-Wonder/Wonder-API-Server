package kugods.wonder.app.auth.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckNameResponse {

    @NotNull
    @Size(min = 3, max = 50)
    private String inputName;

    @NotNull
    private boolean isDuplicated;
}
