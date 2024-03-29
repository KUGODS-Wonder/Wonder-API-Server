package kugods.wonder.app.chat.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {
    public enum MessageType {
        ENTER, LEAVE, TALK;
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
    private String time;
}
