package ru.stnkv.balloongame.api.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import ru.stnkv.balloongame.api.game.dto.*;

/**
 * @author ysitnikov
 * @since 12.03.2021
 */
@RestController
public class GameController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/game/start")
    public void processMessage(@Payload StartGameRequest request) {

        messagingTemplate.convertAndSendToUser(
                request.getRoomId(),"/start/events",
                new StartGameNotification(request.getRoomId()));
    }
}
