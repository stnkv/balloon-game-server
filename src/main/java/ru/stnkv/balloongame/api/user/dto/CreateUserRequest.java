package ru.stnkv.balloongame.api.user.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author ysitnikov
 * @since 12.03.2021
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CreateUserRequest implements Serializable {
    private String username;
}
