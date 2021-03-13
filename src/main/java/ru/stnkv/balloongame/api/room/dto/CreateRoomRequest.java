package ru.stnkv.balloongame.api.room.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author ysitnikov
 * @since 12.03.2021
 */
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class CreateRoomRequest implements Serializable {
    private String name;
    private String userId;
}
