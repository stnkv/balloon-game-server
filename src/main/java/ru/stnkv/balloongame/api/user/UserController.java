package ru.stnkv.balloongame.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stnkv.balloongame.api.user.dto.CreateUserRequest;
import ru.stnkv.balloongame.api.user.dto.CreateUserResponse;
import ru.stnkv.balloongame.api.user.dto.GetUserResponse;
import ru.stnkv.balloongame.domain.user.IUserInteractor;

/**
 * Контроллер для работы с пользователем
 *
 * @author ysitnikov
 * @since 12.03.2021
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private IUserInteractor userInteractor;

    @PostMapping("/create")
    public ResponseEntity<CreateUserResponse> create(@RequestBody CreateUserRequest body) {
        var user = userInteractor.createUser(body.getUsername());
        return new ResponseEntity<>(new CreateUserResponse(user.getId()), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<GetUserResponse> get(@RequestParam String id) throws Exception {
        var user = userInteractor.getUserById(id);
        return new ResponseEntity<>(new GetUserResponse(user.getId(), user.getUsername()), HttpStatus.OK);
    }
}
