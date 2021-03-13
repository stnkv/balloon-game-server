package ru.stnkv.balloongame.api.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.stnkv.balloongame.api.game.dto.inflate.InflateEventReq;
import ru.stnkv.balloongame.api.game.dto.start.ConfirmStartGameReq;
import ru.stnkv.balloongame.api.game.dto.start.StartGameRequest;
import ru.stnkv.balloongame.domain.game.IGameInteractor;

/**
 * @author ysitnikov
 * @since 12.03.2021
 */
@RestController
public class GameController {
    @Autowired
    private IGameInteractor gameInteractor;

    @MessageMapping("/game/start")
    public void processStartGame(@Payload StartGameRequest request) {
        gameInteractor.sendStartGameEvent(request.getRoomId());
    }

    @MessageMapping("/game/inflate")
    public void processInflate(@Payload InflateEventReq request) {
        gameInteractor.sendInflateEventPartitions(request.getRoomId(), request.getUserId());
    }

    @MessageMapping("/game/end")
    public void processEndGame(@Payload InflateEventReq request) {
        gameInteractor.sendEndGameEvent(request.getRoomId());
    }

    @PostMapping("/game/confirm")
    public void confirmForStartGame(@RequestBody ConfirmStartGameReq req) {
        gameInteractor.sendStartGameEvent(req.getRoomId());
    }
}
