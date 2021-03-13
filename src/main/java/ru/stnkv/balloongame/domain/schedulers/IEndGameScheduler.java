package ru.stnkv.balloongame.domain.schedulers;

import ru.stnkv.balloongame.domain.entity.StartGameEntity;

/**
 * @author ysitnikov
 * @since 14.03.2021
 */
public interface IEndGameScheduler {
    void start(StartGameEntity entity);
}
