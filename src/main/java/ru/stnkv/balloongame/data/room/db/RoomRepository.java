package ru.stnkv.balloongame.data.room.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.stnkv.balloongame.common.IdGenerator;
import ru.stnkv.balloongame.data.room.db.dto.Room;
import ru.stnkv.balloongame.domain.entity.CreateRoomEntity;
import ru.stnkv.balloongame.domain.entity.RoomEntity;
import ru.stnkv.balloongame.domain.entity.UserEntity;
import ru.stnkv.balloongame.domain.repository.IRoomRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author ysitnikov
 * @since 14.03.2021
 */
@Component
public class RoomRepository implements IRoomRepository {
    @Autowired
    private RoomDAO roomDAO;
    private final IdGenerator idGenerator = new IdGenerator();

    @Override
    public RoomEntity create(CreateRoomEntity entity) {
        var room = new Room(idGenerator.createID(entity.getName()), entity.getName(), Collections.emptyList());
        room = roomDAO.save(room);
        return new RoomEntity(room.getId(), room.getName(), room.getParticipants());
    }

    @Override
    public void join(RoomEntity roomEntity, UserEntity userEntity) {
        var part = roomEntity.getParticipants();
        List<UserEntity> users;
        if(part != null && !part.isEmpty()) {
            users = new ArrayList<>(roomEntity.getParticipants());
        } else {
            users = new ArrayList<>();
        }
        users.add(userEntity);
        Room room = new Room(roomEntity.getId(), roomEntity.getName(), users);
        roomDAO.save(room);
    }

    @Override
    public Collection<RoomEntity> getAllRooms() {
        return StreamSupport.stream(roomDAO.findAll().spliterator(), false)
                .map(r -> new RoomEntity(r.getId(), r.getName(), r.getParticipants()))
                .collect(Collectors.toList());
    }

    @Override
    public RoomEntity getRoomBy(String id) throws Exception {
        Optional<Room> room = roomDAO.findById(id);
        if(room.isEmpty()) {
            throw new Exception("Room by id " + id + " not found");
        }
        return new RoomEntity(room.get().getId(), room.get().getName(), room.get().getParticipants());
    }
}
