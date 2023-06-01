package kugods.wonder.app.chat.domain;


import kugods.wonder.app.chat.dto.ChatDto;
import kugods.wonder.app.chat.service.ChatService;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Data
public class ChatRoom {
    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId, String name){
        this.roomId = roomId;
        this.name = name;
    }

    public void handleAction(WebSocketSession session, ChatDto message, ChatService service) {
        if (message.getType().equals(ChatDto.MessageType.ENTER)) {
            sessions.add(session);
            message.setMessage(message.getSender() + " 님이 입장하셨습니다");
            sendMessage(message, service);
            return;
        }

        message.setMessage(message.getMessage());
        sendMessage(message, service);
    }

    public <T> void sendMessage(T message, ChatService service) {
        sessions.parallelStream().forEach(session -> service.sendMessage(session, message));
    }
}
