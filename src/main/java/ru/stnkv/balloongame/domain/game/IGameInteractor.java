package ru.stnkv.balloongame.domain.game;

/**
 * @author ysitnikov
 * @since 13.03.2021
 */
public interface IGameInteractor {

    String createGame(String roomId);

    void sendStartGameEvent();

    void sendEventPartitions();

    void sendEndGameEvent();

    void deleteGame(String roomId);

}
