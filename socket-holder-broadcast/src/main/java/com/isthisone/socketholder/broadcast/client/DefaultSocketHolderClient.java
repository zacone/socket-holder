package com.isthisone.socketholder.broadcast.client;

public class DefaultSocketHolderClient implements SocketHolderClient {

    private final String topic;

    private final Caller caller;

    public DefaultSocketHolderClient(Caller caller) {
        this(null, caller);
    }

    public DefaultSocketHolderClient(String topic, Caller caller) {
        this.topic = topic;
        this.caller = caller;
    }

    @Override
    public String getTopic() {
        return topic == null ? DEFAULT_TOPIC_NAME : topic;
    }

    @Override
    public void broadcast(String msg) {
        caller.broadcast(getTopic(), msg);
    }
}
