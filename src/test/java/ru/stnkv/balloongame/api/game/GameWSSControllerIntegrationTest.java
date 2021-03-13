package ru.stnkv.balloongame.api.game;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import ru.stnkv.balloongame.api.game.dto.inflate.InflateEventReq;
import ru.stnkv.balloongame.api.game.dto.start.StartGameRequest;
import ru.stnkv.balloongame.data.game.dto.EndGameNotification;
import ru.stnkv.balloongame.data.game.dto.InflateNotification;
import ru.stnkv.balloongame.data.game.dto.StartGameNotification;
import ru.stnkv.balloongame.domain.entity.RoomEntity;
import ru.stnkv.balloongame.domain.entity.UserEntity;
import ru.stnkv.balloongame.domain.game.checker.ICheckWinner;
import ru.stnkv.balloongame.domain.game.strategy.chance.IGetChance;
import ru.stnkv.balloongame.domain.game.strategy.duration.IGetDuration;
import ru.stnkv.balloongame.domain.game.strategy.question.IGetQuestionNumber;
import ru.stnkv.balloongame.domain.room.IRoomInteractor;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author ysitnikov
 * @since 13.03.2021
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GameWSSControllerIntegrationTest {
    @LocalServerPort
    private Integer port;

    @MockBean
    private ICheckWinner checkWinner;
    @MockBean
    private IRoomInteractor roomInteractor;
    @MockBean
    private IGetQuestionNumber getQuestionNumber;
    @MockBean
    private IGetChance getChance;
    @MockBean
    private IGetDuration getDuration;

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
    public void shouldSendStartGameEvent() throws Exception {
        //Подготовка
        var payload = generator.nextObject(StartGameRequest.class);
        var expected = generator.nextObject(StartGameNotification.class);
        var users = generator.objects(UserEntity.class, 5).collect(Collectors.toList());
        expected.setRoomId(payload.getRoomId());
        expected.setParticipants(users.stream().map(UserEntity::getId).collect(Collectors.toUnmodifiableList()));
        expected.setChance(100);
        expected.setDuration(100);
        expected.setQuestionNumber(1);

        when(getQuestionNumber.get()).thenReturn(1);
        when(getChance.get()).thenReturn(100);
        when(getDuration.get()).thenReturn(100);

        var future = new CompletableFuture<StartGameNotification>();
        var room = mock(RoomEntity.class);
        when(room.getParticipants()).thenReturn(users);
        when(roomInteractor.getRoomBy(any())).thenReturn(room);

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

    @Test
    public void shouldSendInflateEvent() throws Exception {
        var payload = generator.nextObject(InflateEventReq.class);
        var expected = generator.nextObject(InflateNotification.class);
        expected.setRoomId(payload.getRoomId());
        expected.setUserId(payload.getUserId());
        expected.setSize(payload.getSize());

        var future = new CompletableFuture<InflateNotification>();
        StompSession session = webSocketStompClient.connect(getWsPath(), new StompSessionHandlerAdapter() {
        }).get(1, SECONDS);

        session.subscribe("/game/"+ payload.getRoomId() +"/inflate/events", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return InflateNotification.class;
            }
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                future.complete((InflateNotification) payload);
            }
        });
        session.send("/app/game/inflate", payload);

        Assertions.assertEquals(expected, future.get(1, SECONDS));
    }

    @Test
    public void shouldSendEndGameEvent() throws Exception {
        var payload = generator.nextObject(InflateEventReq.class);
        var expected = generator.nextObject(EndGameNotification.class);
        expected.setRoomId(payload.getRoomId());
        expected.setWinnerId(payload.getUserId());
        var future = new CompletableFuture<>();
        StompSession session = webSocketStompClient.connect(getWsPath(), new StompSessionHandlerAdapter() {
        }).get(1, SECONDS);
        when(checkWinner.check(any())).thenReturn(true);

        session.subscribe("/game/"+ payload.getRoomId() +"/end/events", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return EndGameNotification.class;
            }
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                future.complete((EndGameNotification) payload);
            }
        });
        session.send("/app/game/inflate", payload);

        Assertions.assertEquals(expected, future.get(1, SECONDS));
    }

    private String getWsPath() {
        return String.format("ws://localhost:%d/ws-endpoint", port);
    }
}