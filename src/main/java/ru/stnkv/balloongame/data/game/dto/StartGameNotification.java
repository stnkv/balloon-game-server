package ru.stnkv.balloongame.data.game.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * @author ysitnikov
 * @since 13.03.2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StartGameNotification {
    private String roomId;
    private int duration;
    private int chance;
    private int questionNumber;
    private Collection<String> participants;
}
