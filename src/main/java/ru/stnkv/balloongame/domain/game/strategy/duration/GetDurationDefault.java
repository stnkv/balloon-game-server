package ru.stnkv.balloongame.domain.game.strategy.duration;

import org.springframework.stereotype.Component;

/**
 * @author ysitnikov
 * @since 14.03.2021
 */
@Component
public class GetDurationDefault implements IGetDuration{

    @Override
    public int get() {
        return 60000; //мс
    }
}
