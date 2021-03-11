package ru.stnkv.balloongame.api.room;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stnkv.balloongame.api.room.dto.CreateRoomRequest;
import ru.stnkv.balloongame.api.room.dto.CreateRoomResponse;
import ru.stnkv.balloongame.api.room.dto.RoomResponse;
import ru.stnkv.balloongame.domain.room.IRoomInteractor;

import java.util.Collection;
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
                .map(r -> new RoomResponse(r.getId(), r.getName()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<RoomResponse> getBy(@RequestParam String id) {
        var room = roomInteractor.getRoomBy(id);
        return new ResponseEntity<>(new RoomResponse(room.getId(), room.getName()), HttpStatus.OK);
    }
}
