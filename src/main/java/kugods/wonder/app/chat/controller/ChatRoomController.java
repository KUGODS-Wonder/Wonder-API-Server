package kugods.wonder.app.chat.controller;

import kugods.wonder.app.chat.domain.ChatRoom;
import kugods.wonder.app.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;

    @GetMapping("/")
    public String goChatRoom(Model model){
        model.addAttribute("list", chatService.findAllRoom());
        log.info("SHOW ALL ChatList {}", chatService.findAllRoom());
        return "roomlist";
    }

    @PostMapping("/chat/createroom")
    public String createRoom(@RequestParam String name, RedirectAttributes rttr) {
        ChatRoom room = chatService.createChatRoom(name);
        log.info("CREATE Chat Room {}", room);
        rttr.addFlashAttribute("roomName", room);
        return "redirect:/";
    }

    @GetMapping("/chat/room")
    public String roomDetail(Model model, String roomId){
        log.info("roomId {}", roomId);
        model.addAttribute("room", chatService.findRoomById(roomId));
        return "chatroom";
    }
}
