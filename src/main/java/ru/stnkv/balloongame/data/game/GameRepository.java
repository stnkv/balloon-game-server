package ru.stnkv.balloongame.data.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import ru.stnkv.balloongame.data.game.dto.EndGameNotification;
import ru.stnkv.balloongame.data.game.dto.InflateNotification;
import ru.stnkv.balloongame.data.game.dto.StartGameNotification;
import ru.stnkv.balloongame.domain.repository.IGameRepository;

/**
 * @author ysitnikov
 * @since 13.03.2021
 */
@Component
public class GameRepository implements IGameRepository {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendStartGameEvent(String roomId) {
        messagingTemplate.convertAndSendToUser(roomId,"/start/events", new StartGameNotification(roomId));
    }

    @Override
    public void saveInflateEvent(String roomId, String userId) {
        //TODO: Реализовать
    }

    @Override
    public void sendInflateEventToParcipiants(String roomId, String userId) {
        messagingTemplate.convertAndSendToUser(roomId,"/inflate/events", new InflateNotification(roomId, userId));
    }

    @Override
    public void sendEndGameEvent(String roomId) {
        messagingTemplate.convertAndSendToUser(roomId,"/end/events", new EndGameNotification(roomId));
    }
}
