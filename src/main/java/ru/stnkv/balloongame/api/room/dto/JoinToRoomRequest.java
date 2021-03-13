package ru.stnkv.balloongame.api.room.dto;

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
public class JoinToRoomRequest {
    private String userId;
    private String roomId;
}
