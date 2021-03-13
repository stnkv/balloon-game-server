package ru.stnkv.balloongame.domain.room;

import org.springframework.stereotype.Component;
import ru.stnkv.balloongame.domain.entity.RoomEntity;
import ru.stnkv.balloongame.domain.entity.UserEntity;

import java.util.Collection;
import java.util.List;

/**
 * @author ysitnikov
 * @since 12.03.2021
 */
@Component
public class RoomInteractor implements IRoomInteractor {
    @Override
    public RoomEntity create(String name) {
        var par = List.of(new UserEntity("1", "username"), new UserEntity("2", "username"), new UserEntity("3", "username"));
        return new RoomEntity("1", name, par);
    }

    @Override
    public void join(String roomId, String userId) {
        //TODO: РЕАЛИЗОВАТЬ
    }

    @Override
    public Collection<RoomEntity> getAllRooms() {
        var par = List.of(new UserEntity("1", "username"), new UserEntity("2", "username"), new UserEntity("3", "username"));
        var par2 = List.of(new UserEntity("4", "username"), new UserEntity("5", "username"), new UserEntity("6", "username"));
        return List.of(new RoomEntity("1", "roomname", par), new RoomEntity("2", "roomname", par));
    }

    @Override
    public RoomEntity getRoomBy(String id) {
        var par = List.of(new UserEntity("1", "username"), new UserEntity("2", "username"), new UserEntity("3", "username"));
        return new RoomEntity(id, "roomname", par);
    }
}
