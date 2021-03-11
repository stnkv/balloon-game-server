package ru.stnkv.balloongame.domain.user;

import org.springframework.stereotype.Component;

/**
 * @author ysitnikov
 * @since 12.03.2021
 */
@Component
public class UserInteractor implements IUserInteractor {

    @Override
    public UserEntity createUser(String username) {
        return new UserEntity("1", username);
    }

    @Override
    public UserEntity getUserById(String id) {
        return new UserEntity("1", "username");
    }
}
