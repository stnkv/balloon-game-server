package ru.stnkv.balloongame.domain.game;

import org.springframework.stereotype.Component;

/**
 * @author ysitnikov
 * @since 13.03.2021
 */
@Component
public class CheckWinner implements ICheckWinner{
    @Override
    public boolean check(String userId) {
        //TODO: Харкодим что пользователь сразу же выйграл
        return false;
    }
}
