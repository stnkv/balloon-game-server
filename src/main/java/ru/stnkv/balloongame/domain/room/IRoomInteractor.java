package ru.stnkv.balloongame.domain.room;

import ru.stnkv.balloongame.domain.entity.RoomEntity;

import java.util.Collection;

/**
 * @author ysitnikov
 * @since 12.03.2021
 */
public interface IRoomInteractor {

    RoomEntity create(String name, String userId) throws Exception;
    void join(String roomId, String userId) throws Exception;
    Collection<RoomEntity> getAllRooms();
    RoomEntity getRoomBy(String id) throws Exception;
}
