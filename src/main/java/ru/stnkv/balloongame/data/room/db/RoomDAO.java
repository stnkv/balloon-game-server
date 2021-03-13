package ru.stnkv.balloongame.data.room.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.stnkv.balloongame.data.room.db.dto.Room;
import ru.stnkv.balloongame.data.user.db.dto.User;

/**
 * @author ysitnikov
 * @since 14.03.2021
 */
@Repository
public interface RoomDAO extends CrudRepository<Room, String> {
}
