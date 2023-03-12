package com.isthisone.socketholder.subscriber.memo;

import com.isthisone.socketholder.channel.HolderChannel;
import com.isthisone.socketholder.subscriber.Subscriber;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class MemoSubscriber<T> implements Subscriber<T> {

    protected Map<T, Set<String>> subscribeMap = new ConcurrentHashMap<>();

    @Override
    public void subscribe(T key, HolderChannel channel) {
        Set<String> set = subscribeMap.get(key);
        if (set != null) {
            set.add(channel.getId());
        } else {
            set = new ConcurrentSkipListSet<>();
            set.add(channel.getId());
            set = subscribeMap.putIfAbsent(key, set);
            if (set != null) {
                set.add(channel.getId());
            }
        }
    }

    @Override
    public void unsubscribe(HolderChannel channel) {
        for (T key : subscribeMap.keySet()) {
            unsubscribe(key, channel);
        }
    }

    @Override
    public void unsubscribe(T key, HolderChannel channel) {
        Set<String> set = subscribeMap.get(key);
        if (set != null) {
            set.remove(channel.getId());
        }
    }

    @Override
    public Set<String> get(T key) {
        return subscribeMap.get(key);
    }
}
