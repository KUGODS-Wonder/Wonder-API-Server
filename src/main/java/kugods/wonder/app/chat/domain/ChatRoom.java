package kugods.wonder.app.chat.domain;

import lombok.*;

import java.util.*;

@Data
@Builder
public class ChatRoom {
    private String roomId;
    private String roomName;
    private long userCount;
    private HashMap<String, String> userList = new HashMap<>();

    public static ChatRoom create(String roomName) {
        return ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(roomName)
                .build();
    }

    public void incrementUserCount() {
        userCount++;
    }

    public void decrementUserCount() {
        userCount--;
    }

    public String addUser(String userName) {
        String userUUID = UUID.randomUUID().toString();
        userList.put(userUUID, userName);

        return userUUID;
    }

    public String generateNonDuplicateName(String username) {
        String newName = username;

        while (userList.containsValue(newName)) {
            int ranNum = (int) (Math.random() * 100) + 1;

            newName = username + ranNum;
        }

        return newName;
    }

    public void removeUser(String userUUID) {
        userList.remove(userUUID);
    }

    public String getUserName(String userUUID) {
        return userList.get(userUUID);
    }

    public List<String> getUserList() {
        return new ArrayList<>(userList.values());
    }
}
