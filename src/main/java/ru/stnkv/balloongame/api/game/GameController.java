package ru.stnkv.balloongame.api.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import ru.stnkv.balloongame.api.game.dto.ChatMessage;
import ru.stnkv.balloongame.api.game.dto.ChatNotification;
import ru.stnkv.balloongame.api.game.dto.HelloMessage;

/**
 * @author ysitnikov
 * @since 12.03.2021
 */
@RestController
//@RequestMapping("game")
public class GameController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {

        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(),"/queue/messages",
                new ChatNotification(
                        chatMessage.getId(),
                        chatMessage.getSenderId(),
                        "username"));
    }
}
