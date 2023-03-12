package com.isthisone.socketholder.core;

import com.isthisone.socketholder.channel.HolderChannel;
import com.isthisone.socketholder.core.listener.SocketListener;
import com.isthisone.socketholder.core.listener.SocketListenerRegistry;

import java.util.LinkedList;
import java.util.List;

public class ListenableSocketHolder extends SocketHolder implements SocketListenerRegistry {

    private final List<SocketListener> listeners = new LinkedList<>();

    @Override
    public void addListener(SocketListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(SocketListener listener) {
        listeners.remove(listener);
    }

    @Override
    void notifyRegister(HolderChannel channel) {
        for (SocketListener listener : listeners) {
            listener.onSocketRegister(channel);

        }
    }

    @Override
    void notifyUnregister(HolderChannel channel) {
        for (SocketListener listener : listeners) {
            listener.onSocketUnregister(channel);
        }
    }
}
