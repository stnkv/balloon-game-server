package ru.stnkv.balloongame.domain.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.stnkv.balloongame.domain.entity.EndGameEntity;
import ru.stnkv.balloongame.domain.entity.InflateEntity;
import ru.stnkv.balloongame.domain.entity.StartGameEntity;
import ru.stnkv.balloongame.domain.repository.IGameRepository;
import ru.stnkv.balloongame.domain.room.IRoomInteractor;

import java.util.List;

/**
 * @author ysitnikov
 * @since 13.03.2021
 */
@Component
public class GameInteractor implements IGameInteractor {
    @Autowired
    private IGameRepository gameRepository;
    @Autowired
    private ICheckWinner checkWinner;

    @Autowired
    private IRoomInteractor roomInteractor;

    @Override
    public void sendStartGameEvent(String roomId) {
        gameRepository.sendStartGameEvent(StartGameEntity.builder()
                .roomId(roomId)
                .duration(100)
                .chance(100)
                .questionNumber(1)
                .participants(roomInteractor.getRoomBy(roomId).getParticipants())
                .build());
    }

    @Override
    public void sendInflateEventPartitions(String roomId, String senderId) {
        gameRepository.sendInflateEventToParcipiants(InflateEntity.builder()
                .userId(senderId)
                .roomId(roomId)
                .size(0.1D)
                .build());
        if(checkWinner.check(senderId)) {
            sendEndGameEvent(new EndGameEntity(roomId, senderId));
        }
    }

    @Override
    public void sendEndGameEvent(EndGameEntity entity) {
        gameRepository.sendEndGameEvent(entity);
    }

}
