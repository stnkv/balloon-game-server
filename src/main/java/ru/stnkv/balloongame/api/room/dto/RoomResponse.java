package ru.stnkv.balloongame.api.room.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Collection;

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
    private final Collection<ParticipantResponse> participants;

    public RoomResponse(String id, String name, Collection<ParticipantResponse> participants) {
        this.id = id;
        this.name = name;
        this.participants = participants;
    }
}
