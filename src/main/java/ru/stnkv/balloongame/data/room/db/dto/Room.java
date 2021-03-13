package ru.stnkv.balloongame.data.room.db.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import ru.stnkv.balloongame.domain.entity.UserEntity;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author ysitnikov
 * @since 14.03.2021
 */
@Data
@AllArgsConstructor
@RedisHash("Room")
public class Room implements Serializable {
    private String id;
    private String name;
    private Collection<UserEntity> participants;
}
