package ru.stnkv.balloongame.domain.user;

import ru.stnkv.balloongame.domain.entity.UserEntity;

/**
 * @author ysitnikov
 * @since 12.03.2021
 */
public interface IUserInteractor {

    UserEntity createUser(String username);

    UserEntity getUserById(String id) throws Exception;
}
