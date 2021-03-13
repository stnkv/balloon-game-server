package ru.stnkv.balloongame.domain.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.stnkv.balloongame.domain.entity.EndGameEntity;
import ru.stnkv.balloongame.domain.entity.InflateEntity;
import ru.stnkv.balloongame.domain.entity.RoomEntity;
import ru.stnkv.balloongame.domain.entity.StartGameEntity;
import ru.stnkv.balloongame.domain.repository.IGameRepository;
import ru.stnkv.balloongame.domain.repository.IRoomRepository;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author ysitnikov
 * @since 14.03.2021
 */
@Component
public class EndGameScheduler implements IEndGameScheduler {
    @Autowired
    private IRoomRepository roomRepository;
    @Autowired
    private IGameRepository gameRepository;

    public void start(StartGameEntity entity) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> {
            try {
                RoomEntity roomEntity = roomRepository.getRoomBy(entity.getRoomId());
                var user = roomEntity.getParticipants().stream().findFirst();
                if(user.isEmpty()) {
                    throw new Exception("participants not found");
                }
                gameRepository.sendEndGameEvent(new EndGameEntity(roomEntity.getId(), user.get().getId()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }, entity.getDuration(), TimeUnit.MILLISECONDS);
    }
}
