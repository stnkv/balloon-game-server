package ru.stnkv.balloongame.api.user;

import com.google.gson.Gson;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.stnkv.balloongame.api.user.dto.CreateUserRequest;
import ru.stnkv.balloongame.api.user.dto.CreateUserResponse;
import ru.stnkv.balloongame.api.user.dto.GetUserResponse;
import ru.stnkv.balloongame.domain.user.IUserInteractor;
import ru.stnkv.balloongame.domain.entity.UserEntity;

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
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserInteractor userInteractor;

    private Gson gson;
    private EasyRandom generator;

    @BeforeEach
    public void init() {
        gson = new Gson();
        generator = new EasyRandom();
    }

    @Test
    public void shouldCreateUserAndReturnId() throws Exception {
        var expected = generator.nextObject(CreateUserResponse.class);
        var request = generator.nextObject(CreateUserRequest.class);
        when(userInteractor.createUser(any())).thenReturn(new UserEntity(expected.getId(), request.getUsername()));

        var resultAsString = mockMvc.perform(post("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(gson.toJson(expected), resultAsString);
    }

    @Test
    public void shouldGetUserInfo() throws Exception {
        var expected = generator.nextObject(GetUserResponse.class);

        when(userInteractor.getUserById(any())).thenReturn(new UserEntity(expected.getId(), expected.getUsername()));

        var resultAsString = mockMvc.perform(get("/user/get").param("id", expected.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(gson.toJson(expected), resultAsString);
    }
}