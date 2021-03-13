package ru.stnkv.balloongame.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;

/**
 * @author ysitnikov
 * @since 12.03.2021
 */
@ToString
@EqualsAndHashCode
@Getter
public class RoomEntity {
    private final String id;
    private final String name;
    private final Collection<UserEntity> participants;

    public RoomEntity(String id, String name, Collection<UserEntity> participants) {
        this.id = id;
        this.name = name;
        this.participants = participants;
    }
}
