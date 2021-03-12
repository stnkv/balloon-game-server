package ru.stnkv.balloongame.domain.repository;

/**
 * @author ysitnikov
 * @since 13.03.2021
 */
public interface IGameRepository {

    void sendStartGameEvent(String roomId);
    void saveInflateEvent(String roomId, String userId);
    void sendInflateEventToParcipiants(String roomId, String userId);
    void sendEndGameEvent(String roomId);
}
