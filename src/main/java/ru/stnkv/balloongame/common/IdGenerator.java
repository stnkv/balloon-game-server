package ru.stnkv.balloongame.common;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author ysitnikov
 * @since 14.03.2021
 */
public class IdGenerator {

    public String createID(String name) {
        return DigestUtils.md5DigestAsHex(name.getBytes(StandardCharsets.UTF_8));
    }
}
