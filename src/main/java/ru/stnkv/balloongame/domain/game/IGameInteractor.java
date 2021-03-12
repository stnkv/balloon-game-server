package ru.stnkv.balloongame.domain.game;

/**
 * @author ysitnikov
 * @since 13.03.2021
 */
public interface IGameInteractor {

    String createGame(String roomId);

    void sendStartGameEvent(String roomId);

    void sendInflateEventPartitions(String roomId, String senderId);

    void sendEndGameEvent(String roomId);

    void deleteGame(String roomId);

}
