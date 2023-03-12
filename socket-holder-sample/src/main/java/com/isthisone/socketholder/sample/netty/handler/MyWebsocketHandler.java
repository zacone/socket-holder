package com.isthisone.socketholder.sample.netty.handler;

import com.isthisone.socketholder.autoconfigure.socket.SocketHandler;
import com.isthisone.socketholder.autoconfigure.subscriber.DefaultSocketNotify;
import com.isthisone.socketholder.channel.HolderChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MyWebsocketHandler implements SocketHandler {

    private final DefaultSocketNotify defaultSocketNotify;

    @Override
    public void process(HolderChannel channel, String text) {
        log.info("id-{},text-{}", channel.getId(), text);
        defaultSocketNotify.subscribe("test", channel);
    }
}
