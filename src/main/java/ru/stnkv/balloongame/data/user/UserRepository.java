package ru.stnkv.balloongame.data.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.stnkv.balloongame.common.IdGenerator;
import ru.stnkv.balloongame.data.user.db.UserDAO;
import ru.stnkv.balloongame.data.user.db.dto.User;
import ru.stnkv.balloongame.domain.entity.UserEntity;
import ru.stnkv.balloongame.domain.repository.IUserRepository;

/**
 * @author ysitnikov
 * @since 13.03.2021
 */
@Component
public class UserRepository implements IUserRepository {
    @Autowired
    private UserDAO userDAO;
    private final IdGenerator idGenerator = new IdGenerator();

    @Override
    public UserEntity createUser(String name) {
        var user = new User(idGenerator.createID(name), name);
        user = userDAO.save(user);
        return new UserEntity(user.getId(), user.getName());
    }

    @Override
    public UserEntity getUserById(String userId) throws Exception {
        var user = userDAO.findById(userId);
        if(user.isEmpty()) {
            throw new Exception("User by id " + userId + " not found");
        }
        return new UserEntity(user.get().getId(), user.get().getName());
    }
}
