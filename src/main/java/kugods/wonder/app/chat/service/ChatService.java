package kugods.wonder.app.chat.service;

import kugods.wonder.app.chat.domain.ChatRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ChatService {
    private Map<String, ChatRoom> chatRoomMap;

    @PostConstruct
    private void init() {
        chatRoomMap = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
        return new ArrayList<>(chatRoomMap.values());
    }

    public ChatRoom findRoomById(String roomId) {
        return chatRoomMap.get(roomId);
    }

    public ChatRoom createChatRoom(String roomName) {
        ChatRoom chatRoom = ChatRoom.create(roomName);
        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);

        return chatRoom;
    }

    public void plusUserCnt(String roomId) {
        ChatRoom room = findRoomById(roomId);
        room.incrementUserCount();
    }

    public void minusUserCnt(String roomId) {
        ChatRoom room = findRoomById(roomId);
        room.decrementUserCount();
    }

    public String addUser(String roomId, String userName) {
        ChatRoom room = findRoomById(roomId);
        return room.addUser(userName);
    }

    public String isDuplicateName(String roomId, String username) {
        ChatRoom room = findRoomById(roomId);
        return room.generateNonDuplicateName(username);
    }

    public void delUser(String roomId, String userUUID) {
        ChatRoom room = findRoomById(roomId);
        room.removeUser(userUUID);
    }

    public String getUserName(String roomId, String userUUID) {
        ChatRoom room = findRoomById(roomId);
        return room.getUserName(userUUID);
    }

    public List<String> getUserList(String roomId) {
        ChatRoom room = findRoomById(roomId);
        return room.getUserList();
    }
}