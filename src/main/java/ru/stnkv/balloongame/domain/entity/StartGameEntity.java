package ru.stnkv.balloongame.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.util.Collection;

/**
 * @author ysitnikov
 * @since 13.03.2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StartGameEntity {
    private String roomId;
    private int duration;
    private int chance;
    private int questionNumber;
    private Collection<UserEntity> participants;
}
