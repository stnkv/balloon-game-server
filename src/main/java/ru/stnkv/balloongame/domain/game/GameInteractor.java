package ru.stnkv.balloongame.domain.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.stnkv.balloongame.domain.entity.EndGameEntity;
import ru.stnkv.balloongame.domain.entity.InflateEntity;
import ru.stnkv.balloongame.domain.entity.StartGameEntity;
import ru.stnkv.balloongame.domain.game.checker.ICheckWinner;
import ru.stnkv.balloongame.domain.game.strategy.chance.IGetChance;
import ru.stnkv.balloongame.domain.game.strategy.duration.IGetDuration;
import ru.stnkv.balloongame.domain.game.strategy.question.IGetQuestionNumber;
import ru.stnkv.balloongame.domain.repository.IGameRepository;
import ru.stnkv.balloongame.domain.room.IRoomInteractor;

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
    @Autowired
    private IGetDuration getDuration;
    @Autowired
    private IGetChance getChance;
    @Autowired
    private IGetQuestionNumber getQuestionNumber;

    @Override
    public void sendStartGameEvent(String roomId, String userId) throws Exception {
        //TODO: Проверка что игра в статусе создана
        gameRepository.sendStartGameEvent(StartGameEntity.builder()
                .roomId(roomId)
                .duration(getDuration.get())
                .chance(getChance.get())
                .questionNumber(getQuestionNumber.get())
                .participants(roomInteractor.getRoomBy(roomId).getParticipants())
                .build());
    }

    @Override
    public void sendInflateEventPartitions(String roomId, String senderId, Double size) {
        //TODO: Проверка что игра в статусе готова к страрту
        gameRepository.sendInflateEventToParcipiants(InflateEntity.builder()
                .userId(senderId)
                .roomId(roomId)
                .size(size)
                .build());
        if(checkWinner.check(senderId)) {
            sendEndGameEvent(new EndGameEntity(roomId, senderId));
        }
    }

    @Override
    public void sendEndGameEvent(EndGameEntity entity) {
        //TODO: Проверка что игра в статусе прогресса
        gameRepository.sendEndGameEvent(entity);
    }

}
