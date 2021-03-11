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
import ru.stnkv.balloongame.api.room.dto.CreateRoomRequest;
import ru.stnkv.balloongame.api.room.dto.CreateRoomResponse;
import ru.stnkv.balloongame.api.room.dto.RoomResponse;
import ru.stnkv.balloongame.domain.room.IRoomInteractor;
import ru.stnkv.balloongame.domain.room.RoomEntity;

import java.util.ArrayList;
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
        var res = generator.nextObject(CreateRoomResponse.class);
        var req = generator.nextObject(CreateRoomRequest.class);
        when(roomInteractor.create(any())).thenReturn(new RoomEntity(res.getId(), req.getName()));

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
        var list = generator.objects(RoomEntity.class, 5).collect(Collectors.toList());
        var expected = list.stream().map(e -> new RoomResponse(e.getId(), e.getName())).collect(Collectors.toList());
        when(roomInteractor.getAllRooms()).thenReturn(list);

        var result = mockMvc.perform(get("/room/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(gson.toJson(expected), result);
    }

    @Test
    public void shouldGetRoomById() throws Exception {
        var expected = generator.nextObject(RoomResponse.class);
        when(roomInteractor.getRoomBy(expected.getId())).thenReturn(new RoomEntity(expected.getId(), expected.getName()));

        var result = mockMvc.perform(get("/room/get").param("id", expected.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(gson.toJson(expected), result);
    }

}