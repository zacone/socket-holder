package com.isthisone.socketholder.subscriber.memo;

import com.isthisone.socketholder.channel.HolderChannel;
import com.isthisone.socketholder.subscriber.Subscriber;

import java.util.HashSet;
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
            if (set.isEmpty()) {
                subscribeMap.remove(key, set);
            }
        }
    }

    @Override
    public Set<String> get(T key) {
        return subscribeMap.get(key);
    }

    @Override
    public Set<String> getAll() {
        Set<String> set = new HashSet<>();
        for (Set<String> value : subscribeMap.values()) {
            if (!value.isEmpty()) {
                set.addAll(value);
            }
        }
        return set;
    }

    @Override
    public boolean exist(T k) {
        return subscribeMap.containsKey(k);
    }

    @Override
    public boolean exist(HolderChannel channel) {
        for (T key : subscribeMap.keySet()) {
            boolean exist = exist(key, channel);
            if (exist) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean exist(T key, HolderChannel channel) {
        Set<String> set = get(key);
        return set != null && set.contains(channel.getId());
    }
}
