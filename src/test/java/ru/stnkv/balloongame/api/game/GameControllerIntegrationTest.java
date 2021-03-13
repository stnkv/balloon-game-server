package ru.stnkv.balloongame.api.game;

import com.google.gson.Gson;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.stnkv.balloongame.api.game.dto.start.ConfirmStartGameReq;

import java.net.PortUnreachableException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author ysitnikov
 * @since 13.03.2021
 */
@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    private Gson gson;
    private EasyRandom generator;

    @BeforeEach
    public void setUp() {
        gson = new Gson();
        generator = new EasyRandom();
    }

    @Test
    public void shouldConfirm() throws Exception {
        var req = generator.nextObject(ConfirmStartGameReq.class);

        this.mockMvc.perform(post("/game/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(req)))
                .andExpect(status().isOk());
    }
}
