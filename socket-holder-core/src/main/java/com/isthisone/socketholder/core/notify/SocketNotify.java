package com.isthisone.socketholder.core.notify;

import com.isthisone.socketholder.core.ListenableSocketHolder;
import com.isthisone.socketholder.core.SocketHolder;
import com.isthisone.socketholder.core.subscriber.ListenableSubscriber;
import com.isthisone.socketholder.subscriber.Subscriber;

public class SocketNotify<T> extends ListenableSubscriber<T> implements Notify {

    private final SocketHolder socketHolder;

    public SocketNotify(Subscriber<T> subscriber, ListenableSocketHolder socketHolder) {
        super(subscriber, socketHolder);
        this.socketHolder = socketHolder;
    }

    @Override
    public void notifyAll(String msg) {
        socketHolder.sendAll(msg);
    }

}
