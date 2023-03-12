package com.isthisone.socketholder.core.notify;

import com.isthisone.socketholder.core.ListenableSocketHolder;
import com.isthisone.socketholder.core.SocketHolder;
import com.isthisone.socketholder.subscriber.Subscriber;

import java.util.Set;

public class SubscribeSocketNotify<T> extends SocketNotify<T> implements SubscribeNotify<T> {

    private final SocketHolder socketHolder;

    public SubscribeSocketNotify(Subscriber<T> subscriber, ListenableSocketHolder socketHolder) {
        super(subscriber, socketHolder);
        this.socketHolder = socketHolder;
    }

    @Override
    public void notify(T key, String msg) {
        Set<String> set = get(key);
        if (set != null) {
            notify(set, msg);
        }
    }

    private void notify(Set<String> socketIds, String msg) {
        for (String socketId : socketIds) {
            socketHolder.send(socketId, msg);
        }
    }
}
