package com.isthisone.socketholder.sample.netty.broardcast;

import com.isthisone.socketholder.autoconfigure.subscriber.DefaultSocketNotify;
import com.isthisone.socketholder.broadcast.redis.listener.RedisBroadcastListener;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class MyRedisBroadcastListener extends RedisBroadcastListener {

    private static final Logger logger = LoggerFactory.getLogger(MyRedisBroadcastListener.class);

    private final DefaultSocketNotify defaultSocketNotify;

    @Override
    public void listen(String topic, String msg) {
        logger.info("topic-{},msg-{}", topic, msg);
        defaultSocketNotify.notify("test", msg);
        defaultSocketNotify.notifyAll(msg);
    }
}
