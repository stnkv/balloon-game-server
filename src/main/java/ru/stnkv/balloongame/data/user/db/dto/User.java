package ru.stnkv.balloongame.data.user.db.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

/**
 * @author ysitnikov
 * @since 13.03.2021
 */
@Data
@AllArgsConstructor
@RedisHash("User")
public class User implements Serializable {
    private String id;
    private String name;
}
