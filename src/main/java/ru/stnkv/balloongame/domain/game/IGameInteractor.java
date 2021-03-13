package ru.stnkv.balloongame.domain.game;

import ru.stnkv.balloongame.domain.entity.EndGameEntity;

/**
 * @author ysitnikov
 * @since 13.03.2021
 */
public interface IGameInteractor {

    void sendStartGameEvent(String roomId);

    void sendInflateEventPartitions(String roomId, String userId);

    void sendEndGameEvent(EndGameEntity entity);

}
