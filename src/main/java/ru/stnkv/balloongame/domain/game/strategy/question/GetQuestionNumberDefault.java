package ru.stnkv.balloongame.domain.game.strategy.question;

import org.springframework.stereotype.Component;

/**
 * @author ysitnikov
 * @since 14.03.2021
 */
@Component
public class GetQuestionNumberDefault implements IGetQuestionNumber {
    @Override
    public int get() {
        int min = 1;
        int max = 7;
        return (int) ((Math.random() * (max - min)) + min);
    }
}
