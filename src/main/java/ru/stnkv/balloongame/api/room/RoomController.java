package ru.stnkv.balloongame.api.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stnkv.balloongame.api.room.dto.*;
import ru.stnkv.balloongame.domain.room.IRoomInteractor;
import ru.stnkv.balloongame.domain.entity.UserEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * @author ysitnikov
 * @since 12.03.2021
 */
@RestController
@RequestMapping("room")
public class RoomController {

    @Autowired
    private IRoomInteractor roomInteractor;

    @PostMapping("/create")
    public ResponseEntity<CreateRoomResponse> create(@RequestBody CreateRoomRequest body) {
        var room = roomInteractor.create(body.getName());
        return new ResponseEntity<>(new CreateRoomResponse(room.getId()), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<Collection<RoomResponse>> list() {
        var rooms = roomInteractor.getAllRooms();
        var result = rooms.stream()
                .map(r -> new RoomResponse(r.getId(), r.getName(), convert(r.getParticipants())))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/join")
    public void join(@RequestBody JoinToRoomRequest request) throws Exception {
        roomInteractor.join(request.getRoomId(), request.getUserId());
    }

    @GetMapping("/get")
    public ResponseEntity<RoomResponse> getBy(@RequestParam String id) throws Exception {
        var room = roomInteractor.getRoomBy(id);
        return new ResponseEntity<>(new RoomResponse(room.getId(), room.getName(), convert(room.getParticipants())), HttpStatus.OK);
    }

    private Collection<ParticipantResponse> convert(Collection<UserEntity> users) {
        if (users == null || users.isEmpty()) {
            return Collections.emptyList();
        }
        return users.stream().map(u -> new ParticipantResponse(u.getId(), u.getUsername())).collect(Collectors.toUnmodifiableList());
    }
}
