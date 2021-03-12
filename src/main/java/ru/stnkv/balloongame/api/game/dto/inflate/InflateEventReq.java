package ru.stnkv.balloongame.api.game.dto.inflate;

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
public class InflateEventReq {
    private String roomId;
    private String userId;
}
