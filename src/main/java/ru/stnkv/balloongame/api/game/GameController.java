package ru.stnkv.balloongame.api.game;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

/**
 * @author ysitnikov
 * @since 12.03.2021
 */
@RestController
//@RequestMapping("game")
public class GameController {

    @MessageMapping("/welcome")
    @SendTo("/topic/greetings")
    public String greeting(String payload) {
        System.out.println("Generating new greeting message for " + payload);
        return "Hello, " + payload + "!";
    }

    @SubscribeMapping("/chat")
    public HelloMessage sendWelcomeMessageOnSubscription() {
        return new HelloMessage("Hello World!");
    }
}
