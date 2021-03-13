package ru.stnkv.balloongame.data.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import ru.stnkv.balloongame.data.game.dto.EndGameNotification;
import ru.stnkv.balloongame.data.game.dto.InflateNotification;
import ru.stnkv.balloongame.data.game.dto.StartGameNotification;
import ru.stnkv.balloongame.domain.entity.EndGameEntity;
import ru.stnkv.balloongame.domain.entity.InflateEntity;
import ru.stnkv.balloongame.domain.entity.StartGameEntity;
import ru.stnkv.balloongame.domain.entity.UserEntity;
import ru.stnkv.balloongame.domain.repository.IGameRepository;

import java.util.stream.Collectors;

/**
 * @author ysitnikov
 * @since 13.03.2021
 */
@Component
public class GameRepository implements IGameRepository {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendStartGameEvent(StartGameEntity entity) {
        messagingTemplate.convertAndSendToUser(entity.getRoomId(),"/start/events", StartGameNotification.builder()
                .roomId(entity.getRoomId())
                .chance(entity.getChance())
                .duration(entity.getDuration())
                .participants(entity.getParticipants().stream().map(UserEntity::getId).collect(Collectors.toUnmodifiableList()))
                .questionNumber(entity.getQuestionNumber())
                .build());
    }

    @Override
    public void saveInflateEvent(InflateEntity entity) {
        //TODO: Реализовать
    }

    @Override
    public void sendInflateEventToParcipiants(InflateEntity entity) {
        messagingTemplate.convertAndSendToUser(entity.getRoomId(),"/inflate/events", InflateNotification.builder()
                .roomId(entity.getRoomId())
                .userId(entity.getUserId())
                .size(entity.getSize())
                .build());
    }

    @Override
    public void sendEndGameEvent(EndGameEntity entity) {
        messagingTemplate.convertAndSendToUser(entity.getRoomId(),"/end/events", EndGameNotification.builder()
                .roomId(entity.getRoomId())
                .winnerId(entity.getWinnerId())
                .build());
    }
}
