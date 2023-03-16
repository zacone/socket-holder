package com.isthisone.socketholder.sample.netty.handler;

import com.isthisone.socketholder.autoconfigure.socket.SocketHandler;
import com.isthisone.socketholder.autoconfigure.subscriber.StringSubscriberNotify;
import com.isthisone.socketholder.channel.HolderChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class MyWebsocketHandler implements SocketHandler {

    private final StringSubscriberNotify stringSubscriberNotify;

    @Override
    public void process(HolderChannel channel, String text) {
        log.info("id-{},text-{}", channel.getId(), text);
        stringSubscriberNotify.subscribe("test", channel);
    }
}
