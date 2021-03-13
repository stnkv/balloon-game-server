package ru.stnkv.balloongame.domain.game.checker;

import org.springframework.stereotype.Component;
import ru.stnkv.balloongame.domain.entity.InflateEntity;

/**
 * @author ysitnikov
 * @since 13.03.2021
 */
@Component
public class CheckWinner implements ICheckWinner{
    private static final Double MAX_SCORE = 1.5D;
    @Override
    public boolean check(InflateEntity entity) {
        return MAX_SCORE <= entity.getSize();
    }
}
