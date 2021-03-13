package ru.stnkv.balloongame.api.room;

import com.google.gson.Gson;
import org.jeasy.random.EasyRandom;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.stnkv.balloongame.api.room.dto.*;
import ru.stnkv.balloongame.domain.room.IRoomInteractor;
import ru.stnkv.balloongame.domain.room.RoomEntity;
import ru.stnkv.balloongame.domain.user.UserEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author ysitnikov
 * @since 12.03.2021
 */
@SpringBootTest
@AutoConfigureMockMvc
class RoomControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IRoomInteractor roomInteractor;

    private Gson gson;
    private EasyRandom generator;

    @BeforeEach
    public void setUp() {
        gson = new Gson();
        generator = new EasyRandom();
    }

    @Test
    public void shouldCreateRoomAndReturnId() throws Exception {
        var participants = generator.objects(UserEntity.class, 5).collect(Collectors.toList());
        var res = generator.nextObject(CreateRoomResponse.class);
        var req = generator.nextObject(CreateRoomRequest.class);
        when(roomInteractor.create(any())).thenReturn(new RoomEntity(res.getId(), req.getName(), participants));

        var result = mockMvc.perform(post("/room/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(req)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(gson.toJson(res), result);
    }

    @Test
    public void shouldGetAllRooms() throws Exception {
        var participants = generator.objects(UserEntity.class, 5).collect(Collectors.toList());
        var rooms = List.of(new RoomEntity(generator.toString(), generator.toString(), participants));
        var expected = rooms.stream().map(e -> {
            return new RoomResponse(e.getId(), e.getName(), participants.stream().map(u -> new ParticipantResponse(u.getId(), u.getUsername())).collect(Collectors.toUnmodifiableList()));
        }).collect(Collectors.toList());
        when(roomInteractor.getAllRooms()).thenReturn(rooms);

        var result = mockMvc.perform(get("/room/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(gson.toJson(expected), result);
    }

    @Test
    public void shouldJoinParticipiantInRoom() throws Exception {
        var participant = generator.nextObject(JoinToRoomRequest.class);

        mockMvc.perform(post("/room/join")
                .content(gson.toJson(participant))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetRoomById() throws Exception {
        var participants = generator.objects(UserEntity.class, 5).collect(Collectors.toList());
        var room = new RoomEntity(generator.toString(), generator.toString(), participants);
        var expected = new RoomResponse(room.getId(), room.getName(), participants.stream().map(u -> new ParticipantResponse(u.getId(), u.getUsername())).collect(Collectors.toUnmodifiableList()));
        when(roomInteractor.getRoomBy(expected.getId())).thenReturn(room);

        var result = mockMvc.perform(get("/room/get").param("id", expected.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(gson.toJson(expected), result);
    }

}