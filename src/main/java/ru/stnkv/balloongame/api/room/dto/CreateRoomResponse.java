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
public class CreateRoomResponse implements Serializable {
    private final String id;

    public CreateRoomResponse(String id) {
        this.id = id;
    }
}
