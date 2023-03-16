package com.isthisone.socketholder.sample.netty.broardcast;

import com.isthisone.socketholder.autoconfigure.subscriber.StringSubscriberNotify;
import com.isthisone.socketholder.broadcast.redis.listener.RedisBroadcastListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class MyRedisBroadcastListener extends RedisBroadcastListener {

    private final StringSubscriberNotify stringSubscriberNotify;

    @Override
    public void listen(String topic, String msg) {
        log.info("topic-{},msg-{}", topic, msg);
        stringSubscriberNotify.notify("test", msg);
        stringSubscriberNotify.notifyAll(msg);
    }
}
