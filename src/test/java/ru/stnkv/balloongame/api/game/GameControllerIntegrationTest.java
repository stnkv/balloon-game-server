package ru.stnkv.balloongame.api.game;

import org.jeasy.random.EasyRandom;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import ru.stnkv.balloongame.api.game.dto.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author ysitnikov
 * @since 13.03.2021
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GameControllerIntegrationTest {
    @LocalServerPort
    private Integer port;

    private WebSocketStompClient webSocketStompClient;
    private EasyRandom generator;


    @BeforeEach
    public void setup() {
        this.webSocketStompClient = new WebSocketStompClient(new SockJsClient(
                List.of(new WebSocketTransport(new StandardWebSocketClient()))));
        this.webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
        generator = new EasyRandom();
    }

    @Test
    public void shouldCreateGame() throws Exception {

    }

    @Test
    public void shouldSendStartGameEvent() throws Exception {
        //Подготовка
        var payload = generator.nextObject(StartGameRequest.class);
        var expected = new StartGameNotification(payload.getRoomId());
        var future = new CompletableFuture<StartGameNotification>();
        StompSession session = webSocketStompClient.connect(getWsPath(), new StompSessionHandlerAdapter() {
        }).get(1, SECONDS);
        //Вызов
        session.subscribe("/game/"+ payload.getRoomId() +"/start/events", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return StartGameNotification.class;
            }
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                future.complete((StartGameNotification) payload);
            }
        });
        session.send("/app/game/start", payload);
        //Проверка
        Assertions.assertEquals(expected, future.get(1, SECONDS));
    }

    private String getWsPath() {
        return String.format("ws://localhost:%d/ws-endpoint", port);
    }
}