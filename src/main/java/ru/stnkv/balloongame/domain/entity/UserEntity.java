package ru.stnkv.balloongame.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author ysitnikov
 * @since 12.03.2021
 */
@Getter
@EqualsAndHashCode
@ToString
public class UserEntity {
    private final String id;
    private final String username;

    public UserEntity(String id, String username) {
        this.id = id;
        this.username = username;
    }
}
