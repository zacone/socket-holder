package com.isthisone.socketholder.core.notify;

import com.isthisone.socketholder.core.ListenableSocketHolder;
import com.isthisone.socketholder.core.SocketNotify;
import com.isthisone.socketholder.core.subscriber.ListenableSubscriber;
import com.isthisone.socketholder.subscriber.Subscriber;

import java.util.Set;

public class DefaultSubscriberNotify<T> extends ListenableSubscriber<T> implements SubscriberNotify<T> {

    private final SocketNotify socketNotify;

    public DefaultSubscriberNotify(Subscriber<T> subscriber, ListenableSocketHolder listenableSocketHolder) {
        super(subscriber, listenableSocketHolder);
        this.socketNotify = listenableSocketHolder;
    }

    @Override
    public void notify(T key, String msg) {
        Set<String> set = get(key);
        if (set != null) {
            send(set, msg);
        }
    }

    @Override
    public void notifyAndExclude(T key, String msg, Set<String> socketIds) {
        Set<String> set = get(key);
        if (set != null) {
            for (String socketId : set) {
                if (!socketIds.contains(socketId)) {
                    send(socketId, msg);
                }
            }
        }
    }

    @Override
    public void notifyAll(String msg) {
        Set<String> set = getAll();
        if (set != null) {
            send(set, msg);
        }
    }

    @Override
    public void notifyAllAndExclude(String msg, Set<String> socketIds) {
        Set<String> set = getAll();
        if (set != null) {
            for (String socketId : set) {
                if (!socketIds.contains(socketId)) {
                    send(socketId, msg);
                }
            }
        }
    }

    private void send(Set<String> socketIds, String msg) {
        socketNotify.send(socketIds, msg);
    }

    private void send(String socketId, String msg) {
        socketNotify.send(socketId, msg);
    }

}
