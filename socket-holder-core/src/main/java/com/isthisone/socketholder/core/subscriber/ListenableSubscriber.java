package com.isthisone.socketholder.core.subscriber;

import com.isthisone.socketholder.channel.HolderChannel;
import com.isthisone.socketholder.core.listener.SocketListener;
import com.isthisone.socketholder.core.listener.SocketListenerRegistry;
import com.isthisone.socketholder.subscriber.Subscriber;

import java.util.Set;

public class ListenableSubscriber<T> implements Subscriber<T>, SocketListener {

    private final Subscriber<T> subscriber;

    private final SocketListenerRegistry registry;

    public ListenableSubscriber(Subscriber<T> subscriber, SocketListenerRegistry registry) {
        this.subscriber = subscriber;
        this.registry = registry;
        registry.addListener(this);
    }

    @Override
    public void subscribe(T key, HolderChannel channel) {
        subscriber.subscribe(key, channel);
    }

    @Override
    public void unsubscribe(HolderChannel channel) {
        subscriber.unsubscribe(channel);
    }

    @Override
    public void unsubscribe(T key, HolderChannel channel) {
        subscriber.unsubscribe(key, channel);
    }

    @Override
    public Set<String> get(T k) {
        return subscriber.get(k);
    }

    @Override
    public void onSocketRegister(HolderChannel channel) {
        //ignore
    }

    @Override
    public void onSocketUnregister(HolderChannel channel) {
        unsubscribe(channel);
    }

    public void destroy() {
        registry.removeListener(this);
    }
}
