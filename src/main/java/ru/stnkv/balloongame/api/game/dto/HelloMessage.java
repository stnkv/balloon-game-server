package ru.stnkv.balloongame.api.game.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author ysitnikov
 * @since 12.03.2021
 */
@ToString
@EqualsAndHashCode
@Getter
public class HelloMessage implements Serializable {
    private final String name;

    public HelloMessage(String name) {
        this.name = name;

    }
}
