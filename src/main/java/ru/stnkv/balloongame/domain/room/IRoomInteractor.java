package ru.stnkv.balloongame.domain.room;

import java.util.Collection;

/**
 * @author ysitnikov
 * @since 12.03.2021
 */
public interface IRoomInteractor {

    RoomEntity create(String name);

    Collection<RoomEntity> getAllRooms();
    RoomEntity getRoomBy(String id);
}
