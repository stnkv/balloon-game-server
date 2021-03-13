package ru.stnkv.balloongame.domain.schedulers;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author ysitnikov
 * @since 14.03.2021
 */
@EnableAsync
public class EndGameScheduler implements IEndGameScheduler {

    @Async
    @Scheduled(initialDelay = 1000)
    public void start() {
        System.out.println("Fixed rate task with one second initial delay");
    }
}
