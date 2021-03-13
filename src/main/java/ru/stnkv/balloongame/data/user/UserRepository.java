package ru.stnkv.balloongame.data.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import ru.stnkv.balloongame.data.user.db.UserDAO;
import ru.stnkv.balloongame.data.user.db.dto.User;
import ru.stnkv.balloongame.domain.entity.UserEntity;
import ru.stnkv.balloongame.domain.repository.IUserRepository;

import java.nio.charset.StandardCharsets;

/**
 * @author ysitnikov
 * @since 13.03.2021
 */
@Component
public class UserRepository implements IUserRepository {
    @Autowired
    private UserDAO userDAO;

    @Override
    public UserEntity createUser(String name) {
        var user = new User(DigestUtils.md5DigestAsHex(name.getBytes(StandardCharsets.UTF_8)), name);
        user = userDAO.save(user);
        return new UserEntity(user.getId(), user.getName());
    }

    @Override
    public UserEntity getUserById(String userId) throws Exception {
        var user = userDAO.findById(userId);
        if(user.isEmpty()) {
            throw new Exception("User by id not " + userId + " found");
        }
        return new UserEntity(user.get().getId(), user.get().getName());
    }
}
