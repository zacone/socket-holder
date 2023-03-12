package com.isthisone.socketholder.broadcast.redis.listener;

import com.isthisone.socketholder.broadcast.client.SocketHolderClient;
import com.isthisone.socketholder.broadcast.listener.BroadcastListener;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.nio.charset.StandardCharsets;

public abstract class RedisBroadcastListener implements BroadcastListener, MessageListener {

    @Override
    public String topic() {
        return SocketHolderClient.DEFAULT_TOPIC_NAME;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String topic = new String(message.getChannel(), StandardCharsets.UTF_8);
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        listen(topic, msg);
    }

}
