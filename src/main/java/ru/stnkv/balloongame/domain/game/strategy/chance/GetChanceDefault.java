package ru.stnkv.balloongame.domain.game.strategy.chance;

import org.springframework.stereotype.Component;

/**
 * @author ysitnikov
 * @since 14.03.2021
 */
@Component
public class GetChanceDefault implements IGetChance{
    @Override
    public int get() {
        int min = 0;
        int max = 100;
        return (int) ((Math.random() * (max - min)) + min);
    }
}
