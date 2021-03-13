package ru.stnkv.balloongame.domain.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.stnkv.balloongame.domain.entity.CreateRoomEntity;
import ru.stnkv.balloongame.domain.entity.RoomEntity;
import ru.stnkv.balloongame.domain.entity.UserEntity;
import ru.stnkv.balloongame.domain.repository.IRoomRepository;
import ru.stnkv.balloongame.domain.repository.IUserRepository;

import java.util.Collection;
import java.util.List;

/**
 * @author ysitnikov
 * @since 12.03.2021
 */
@Component
public class RoomInteractor implements IRoomInteractor {
    @Autowired
    private IRoomRepository roomRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public RoomEntity create(String name, String userId) throws Exception {
        UserEntity userEntity = userRepository.getUserById(userId);
        RoomEntity roomEntity = roomRepository.create(new CreateRoomEntity(name));
        roomRepository.join(roomEntity, userEntity);
        return roomEntity;
    }

    @Override
    public void join(String roomId, String userId) throws Exception {
        RoomEntity entity = roomRepository.getRoomBy(roomId);
        UserEntity user = userRepository.getUserById(userId);
        roomRepository.join(entity, user);
    }

    @Override
    public Collection<RoomEntity> getAllRooms() {
        return roomRepository.getAllRooms();
    }

    @Override
    public RoomEntity getRoomBy(String id) throws Exception {
        return roomRepository.getRoomBy(id);
    }
}
