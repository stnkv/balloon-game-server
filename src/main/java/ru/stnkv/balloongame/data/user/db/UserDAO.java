package ru.stnkv.balloongame.data.user.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.stnkv.balloongame.data.user.db.dto.User;

/**
 * @author ysitnikov
 * @since 13.03.2021
 */
@Repository
public interface UserDAO extends CrudRepository<User, String> {

}
