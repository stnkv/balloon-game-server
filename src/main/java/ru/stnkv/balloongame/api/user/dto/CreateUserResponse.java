package ru.stnkv.balloongame.api.user.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author ysitnikov
 * @since 12.03.2021
 */
@Getter
@EqualsAndHashCode
@ToString
public class CreateUserResponse implements Serializable {
    private final @NonNull String id;

    public CreateUserResponse(String id) {
        this.id = id;
    }
}
