package ru.stnkv.balloongame.domain.room;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author ysitnikov
 * @since 12.03.2021
 */
@Component
public class RoomInteractor implements IRoomInteractor {
    @Override
    public RoomEntity create(String name) {
        return new RoomEntity("1", name);
    }

    @Override
    public Collection<RoomEntity> getAllRooms() {
        return List.of(new RoomEntity("1", "name"), new RoomEntity("2", "name"));
    }

    @Override
    public RoomEntity getRoomBy(String id) {
        return new RoomEntity(id, "name");
    }
}
