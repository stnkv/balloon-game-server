package ru.stnkv.balloongame.api.game;

import com.google.gson.Gson;
import org.jeasy.random.EasyRandom;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;

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
    public void shouldGetMessage() throws Exception {
        // Подготовка
        var msg = generator.nextObject(ChatMessage.class);
        var expected = new ChatNotification(msg.getId(), msg.getSenderId(), msg.getRecipientId());
        var future = new CompletableFuture<ChatNotification>();
        var session = webSocketStompClient.connect(getWsPath(), new StompSessionHandlerAdapter() {
        }).get(1, SECONDS);
        session.subscribe("/user/"+ msg.getRecipientId() + "/queue/messages", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return ChatNotification.class;
            }
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                future.complete((ChatNotification) payload);
            }
        });
        // Вызов
        session.send("/app/chat", msg);
        // Проверка
        Assertions.assertEquals(expected, future.get(1, SECONDS));

    }

    private String getWsPath() {
        return String.format("ws://localhost:%d/ws-endpoint", port);
    }
}