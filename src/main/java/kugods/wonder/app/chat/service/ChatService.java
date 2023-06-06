package kugods.wonder.app.chat.service;

import kugods.wonder.app.chat.dto.ChatRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
@Service
public class ChatService {

    private Map<String, ChatRoom> chatRoomMap;

    @PostConstruct
    private void initializeChatRoomMap() {
        this.chatRoomMap = new LinkedHashMap<>();
    }

    public List<ChatRoom> getAllRooms() {
        return new ArrayList<>(this.chatRoomMap.values());
    }

    public ChatRoom getRoomById(String roomId){
        return this.chatRoomMap.get(roomId);
    }

    public ChatRoom createChatRoom(String roomName){
        ChatRoom newChatRoom = new ChatRoom().create(roomName);
        this.chatRoomMap.put(newChatRoom.getRoomId(), newChatRoom);
        return newChatRoom;
    }

    public void incrementUserCount(String roomId) {
        updateUserCount(roomId, 1);
    }

    public void decrementUserCount(String roomId) {
        updateUserCount(roomId, -1);
    }

    public String addUserToRoom(String roomId, String userName){
        ChatRoom room = getRoomById(roomId);
        String userUUID = UUID.randomUUID().toString();
        room.getUserList().put(userUUID, userName);
        return userUUID;
    }

    public String createUniqueUserName(String roomId, String userName) {
        ChatRoom room = getRoomById(roomId);
        String uniqueUserName = userName;

        while(room.getUserList().containsValue(uniqueUserName)) {
            int randomNum = new Random().nextInt(100) + 1;
            uniqueUserName = userName + randomNum;
        }

        return uniqueUserName;
    }

    public void removeUserFromRoom(String roomId, String userUUID) {
        ChatRoom room = getRoomById(roomId);
        room.getUserList().remove(userUUID);
    }

    public String getUserName(String roomId, String userUUID) {
        ChatRoom room = getRoomById(roomId);
        return room.getUserList().get(userUUID);
    }

    public List<String> getAllUsersInRoom(String roomId){
        return new ArrayList<>(getRoomById(roomId).getUserList().values());
    }

    private void updateUserCount(String roomId, int count) {
        ChatRoom room = getRoomById(roomId);
        room.setUserCount(room.getUserCount() + count);
    }
}
