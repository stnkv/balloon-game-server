package ru.stnkv.balloongame.api.user.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author ysitnikov
 * @since 12.03.2021
 */
@Getter
@ToString
@EqualsAndHashCode
public class GetUserResponse {
    private final String id;
    private final String username;

    public GetUserResponse(String id, String username) {
        this.id = id;
        this.username = username;
    }
}
