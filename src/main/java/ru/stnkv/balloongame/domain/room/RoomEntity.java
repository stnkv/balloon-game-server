package ru.stnkv.balloongame.domain.room;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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

    public RoomEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
