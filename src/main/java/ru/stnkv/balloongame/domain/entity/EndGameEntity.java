package ru.stnkv.balloongame.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ysitnikov
 * @since 13.03.2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EndGameEntity {
    private String roomId;
    private String winnerId;
}
