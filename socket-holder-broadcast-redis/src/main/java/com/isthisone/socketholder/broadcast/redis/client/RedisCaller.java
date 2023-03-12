package com.isthisone.socketholder.broadcast.redis.client;

import com.isthisone.socketholder.broadcast.client.Caller;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisCaller implements Caller {

    private final StringRedisTemplate stringRedisTemplate;

    public RedisCaller(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void broadcast(String topic, String msg) {
        stringRedisTemplate.convertAndSend(topic, msg);
    }
}
