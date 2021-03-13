package ru.stnkv.balloongame.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.stnkv.balloongame.domain.entity.UserEntity;
import ru.stnkv.balloongame.domain.repository.IUserRepository;

/**
 * @author ysitnikov
 * @since 12.03.2021
 */
@Component
public class UserInteractor implements IUserInteractor {
    @Autowired
    IUserRepository userRepository;

    @Override
    public UserEntity createUser(String username) {
        return userRepository.createUser(username);
    }

    @Override
    public UserEntity getUserById(String id) throws Exception {
        return userRepository.getUserById(id);
    }
}
