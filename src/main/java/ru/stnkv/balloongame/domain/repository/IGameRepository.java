package ru.stnkv.balloongame.domain.repository;

import ru.stnkv.balloongame.domain.entity.EndGameEntity;
import ru.stnkv.balloongame.domain.entity.InflateEntity;
import ru.stnkv.balloongame.domain.entity.StartGameEntity;

/**
 * @author ysitnikov
 * @since 13.03.2021
 */
public interface IGameRepository {

    void sendStartGameEvent(StartGameEntity entity);
    void saveInflateEvent(InflateEntity entity);
    void sendInflateEventToParcipiants(InflateEntity entity);
    void sendEndGameEvent(EndGameEntity entity);
}
