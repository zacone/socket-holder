package com.isthisone.socketholder.core;

import com.isthisone.socketholder.channel.HolderChannel;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SocketHolder implements SocketRegistry, SocketNotify {

    protected final Map<String, HolderChannel> channelMap = new ConcurrentHashMap<>();

    public HolderChannel getChannel(String socketId) {
        return channelMap.get(socketId);
    }

    @Override
    public void register(HolderChannel channel) {
        HolderChannel oldChannel = channelMap.putIfAbsent(channel.getId(), channel);
        if (oldChannel != null) {
            if (oldChannel != channel) {
                channelMap.put(channel.getId(), channel);
                unregister(oldChannel);
            }
        }
    }

    @Override
    public boolean unregister(HolderChannel channel) {
        return channelMap.remove(channel.getId(), channel);
    }

    @Override
    public void send(String socketId, String msg) {
        HolderChannel channel = channelMap.get(socketId);
        if (channel != null && channel.isOpen()) {
            channel.send(msg);
        }
    }

    @Override
    public void send(Set<String> socketIds, String msg) {
        for (String socketId : socketIds) {
            send(socketId, msg);
        }
    }

    @Override
    public void sendAll(String msg) {
        for (HolderChannel channel : channelMap.values()) {
            send(channel.getId(), msg);
        }
    }
}
