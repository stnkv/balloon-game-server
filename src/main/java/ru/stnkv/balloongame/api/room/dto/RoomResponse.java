package ru.stnkv.balloongame.api.room.dto;

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
public class RoomResponse implements Serializable {
    private final String id;
    private final String name;

    public RoomResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
