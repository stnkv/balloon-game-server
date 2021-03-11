package ru.stnkv.balloongame.api.game;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

/**
 * @author ysitnikov
 * @since 12.03.2021
 */
@RestController
@RequestMapping("game")
public class GameController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public HelloMessage greeting() throws Exception {
        Thread.sleep(1000); // simulated delay
        return new HelloMessage("Hello!");
    }
}
