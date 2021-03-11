package ru.stnkv.balloongame.domain.user;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author ysitnikov
 * @since 12.03.2021
 */
public interface IUserInteractor {

    UserEntity createUser(String username);

    UserEntity getUserById(String id);
}
