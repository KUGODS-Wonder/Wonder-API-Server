package kugods.wonder.app.walk.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TagInfo implements Serializable {
    private static final long serialVersionUID = 994329189L;
    private Long tagId;

    private String name;
}
