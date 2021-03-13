package ru.stnkv.balloongame.domain.game.checker;

import ru.stnkv.balloongame.domain.entity.InflateEntity;

/**
 * @author ysitnikov
 * @since 13.03.2021
 */
public interface ICheckWinner {
    boolean check(InflateEntity entity);
}
