package kugods.wonder.app.chat.controller;

import kugods.wonder.app.chat.dto.ChatDto;
import kugods.wonder.app.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations template;
    private final ChatService chatService;


    @MessageMapping("/chat/enterUser")
    public void enterUser(@Payload ChatDto chat, SimpMessageHeaderAccessor headerAccessor) {
        String roomId = chat.getRoomId();
        chatService.plusUserCnt(roomId);

        String userUUID = chatService.addUser(roomId, chat.getSender());
        setSessionAttributes(headerAccessor, userUUID, roomId);

        String message = generateMessage(chat.getSender(), " 님 입장!!");
        chat.setMessage(message);

        publishToChatRoom(roomId, chat);
    }

    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload ChatDto chat) {
        log.info("CHAT {}", chat);
        chat.setMessage(chat.getMessage());

        publishToChatRoom(chat.getRoomId(), chat);
    }

    @EventListener
    public void handleWebSocketDisconnect(SessionDisconnectEvent event) {
        log.info("DisConnEvent {}", event);

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String userUUID = (String) headerAccessor.getSessionAttributes().get("userUUID");
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");

        log.info("headAccessor {}", headerAccessor);

        processUserDeparture(roomId, userUUID);
    }

    @GetMapping("/chat/userlist")
    @ResponseBody
    public List<String> userList(String roomId) {
        return chatService.getUserList(roomId);
    }

    @GetMapping("/chat/duplicateName")
    @ResponseBody
    public String isDuplicateName(@RequestParam("roomId") String roomId, @RequestParam("username") String username) {
        String userName = chatService.isDuplicateName(roomId, username);
        log.info("동작확인 {}", userName);
        return userName;
    }

    private void setSessionAttributes(SimpMessageHeaderAccessor headerAccessor, String userUUID, String roomId) {
        headerAccessor.getSessionAttributes().put("userUUID", userUUID);
        headerAccessor.getSessionAttributes().put("roomId", roomId);
    }

    private void publishToChatRoom(String roomId, ChatDto chat) {
        template.convertAndSend("/sub/chat/room/" + roomId, chat);
    }

    private String generateMessage(String username, String action) {
        return username + action;
    }

    private void processUserDeparture(String roomId, String userUUID) {
        chatService.minusUserCnt(roomId);

        String username = chatService.getUserName(roomId, userUUID);
        chatService.delUser(roomId, userUUID);

        if (username != null) {
            log.info("User Disconnected : " + username);
            ChatDto chat = ChatDto.builder()
                    .type(ChatDto.MessageType.LEAVE)
                    .sender(username)
                    .message(generateMessage(username, " 님 퇴장!!"))
                    .build();

            publishToChatRoom(roomId, chat);
        }
    }
}
