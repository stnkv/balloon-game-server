package ru.stnkv.balloongame.domain.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.stnkv.balloongame.domain.repository.IGameRepository;

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

    @Override
    public String createGame(String roomId) {
        return null;
    }

    @Override
    public void sendStartGameEvent(String roomId) {
        gameRepository.sendStartGameEvent(roomId);
    }

    @Override
    public void sendInflateEventPartitions(String roomId, String senderId) {
        gameRepository.sendInflateEventToParcipiants(roomId, senderId);
        if(checkWinner.check(senderId)) {
            sendEndGameEvent(roomId);
        }
    }

    @Override
    public void sendEndGameEvent(String roomId) {
        gameRepository.sendEndGameEvent(roomId);
    }

    @Override
    public void deleteGame(String roomId) {

    }
}
