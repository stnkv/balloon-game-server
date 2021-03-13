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
import ru.stnkv.balloongame.domain.schedulers.EndGameScheduler;
import ru.stnkv.balloongame.domain.schedulers.IEndGameScheduler;

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
    @Autowired
    private IEndGameScheduler endGameScheduler;

    @Override
    public void sendStartGameEvent(String roomId, String userId) throws Exception {
        //TODO: Проверка что игра в статусе создана
        StartGameEntity entity = StartGameEntity.builder()
                .roomId(roomId)
                .duration(getDuration.get())
                .chance(getChance.get())
                .questionNumber(getQuestionNumber.get())
                .participants(roomInteractor.getRoomBy(roomId).getParticipants())
                .build();
        gameRepository.sendStartGameEvent(entity);
        endGameScheduler.start(entity);
    }

    @Override
    public void sendInflateEventPartitions(String roomId, String senderId, Double size) {
        //TODO: Проверка что игра в статусе готова к страрту
        var inflateEntity = InflateEntity.builder()
                .userId(senderId)
                .roomId(roomId)
                .size(size)
                .build();
        gameRepository.sendInflateEventToParcipiants(inflateEntity);
        if (checkWinner.check(inflateEntity)) {
            sendEndGameEvent(new EndGameEntity(roomId, senderId));
        }
    }

    @Override
    public void sendEndGameEvent(EndGameEntity entity) {
        //TODO: Проверка что игра в статусе прогресса
        gameRepository.sendEndGameEvent(entity);
    }

}
