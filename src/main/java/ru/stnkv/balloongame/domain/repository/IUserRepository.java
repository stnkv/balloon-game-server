package ru.stnkv.balloongame.domain.repository;

import ru.stnkv.balloongame.domain.entity.UserEntity;

/**
 * @author ysitnikov
 * @since 13.03.2021
 */
public interface IUserRepository {
    UserEntity createUser(String name);
    UserEntity getUserById(String userId) throws Exception;
}
