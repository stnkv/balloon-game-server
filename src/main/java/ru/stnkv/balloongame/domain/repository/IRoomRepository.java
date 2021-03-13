package ru.stnkv.balloongame.domain.repository;

import ru.stnkv.balloongame.domain.entity.CreateRoomEntity;
import ru.stnkv.balloongame.domain.entity.RoomEntity;
import ru.stnkv.balloongame.domain.entity.UserEntity;

import java.util.Collection;

/**
 * @author ysitnikov
 * @since 14.03.2021
 */
public interface IRoomRepository {
    RoomEntity create(CreateRoomEntity name);
    void join(RoomEntity roomEntity, UserEntity userEntity);
    Collection<RoomEntity> getAllRooms();
    RoomEntity getRoomBy(String id) throws Exception;
}
