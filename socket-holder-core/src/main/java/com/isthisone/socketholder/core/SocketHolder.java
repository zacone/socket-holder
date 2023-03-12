package com.isthisone.socketholder.core;

import com.isthisone.socketholder.channel.HolderChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class SocketHolder {

    private final Map<String, HolderChannel> channelMap = new ConcurrentHashMap<>();

    public void sendAll(String msg) {
        for (HolderChannel channel : channelMap.values()) {
            if (channel != null && channel.isOpen()) {
                channel.send(msg);
            }
        }
    }

    public void send(String socketId, String msg) {
        HolderChannel channel = channelMap.get(socketId);
        if (channel != null && channel.isOpen()) {
            channel.send(msg);
        }
    }

    public void register(HolderChannel channel) {
        HolderChannel oldChannel = channelMap.putIfAbsent(channel.getId(), channel);
        if (oldChannel != null) {
            if (oldChannel != channel) {
                channelMap.put(channel.getId(), channel);
                unregister(oldChannel);
            }
        }
        notifyRegister(channel);
    }

    public void unregister(HolderChannel channel) {
        boolean success = channelMap.remove(channel.getId(), channel);
        if (success) {
            notifyUnregister(channel);
        }
    }

    abstract void notifyRegister(HolderChannel channel);

    abstract void notifyUnregister(HolderChannel channel);
}
