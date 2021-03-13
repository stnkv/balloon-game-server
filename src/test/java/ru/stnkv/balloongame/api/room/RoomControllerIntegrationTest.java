package ru.stnkv.balloongame.api.room;

import com.google.gson.Gson;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.stnkv.balloongame.api.room.dto.*;
import ru.stnkv.balloongame.data.room.db.RoomDAO;
import ru.stnkv.balloongame.data.room.db.dto.Room;
import ru.stnkv.balloongame.data.user.db.UserDAO;
import ru.stnkv.balloongame.data.user.db.dto.User;
import ru.stnkv.balloongame.domain.entity.UserEntity;

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
    private RoomDAO roomDAO;
    @MockBean
    private UserDAO userDAO;

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
        var user = generator.nextObject(User.class);
        when(roomDAO.save(any())).thenReturn(new Room(res.getId(), req.getName(), participants));
        when(userDAO.findById(any())).thenReturn(java.util.Optional.of(user));
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
        var rooms = List.of(new Room(generator.toString(), generator.toString(), participants));
        var expected = rooms.stream().map(e -> {
            return new RoomResponse(e.getId(), e.getName(), participants.stream().map(u -> new ParticipantResponse(u.getId(), u.getUsername())).collect(Collectors.toUnmodifiableList()));
        }).collect(Collectors.toList());
        when(roomDAO.findAll()).thenReturn(rooms);

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
        var room = generator.nextObject(Room.class);
        var user = generator.nextObject(User.class);
        when(userDAO.findById(any())).thenReturn(java.util.Optional.of(user));
        when(roomDAO.findById(any())).thenReturn(java.util.Optional.of(room));
        when(roomDAO.save(any())).thenReturn(room);

        mockMvc.perform(post("/room/join")
                .content(gson.toJson(participant))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetRoomById() throws Exception {
        var participants = generator.objects(UserEntity.class, 5).collect(Collectors.toList());
        var room = new Room(generator.toString(), generator.toString(), participants);
        var expected = new RoomResponse(room.getId(), room.getName(), participants.stream().map(u -> new ParticipantResponse(u.getId(), u.getUsername())).collect(Collectors.toUnmodifiableList()));
        when(roomDAO.findById(any())).thenReturn(java.util.Optional.of(room));

        var result = mockMvc.perform(get("/room/get").param("id", expected.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(gson.toJson(expected), result);
    }

}