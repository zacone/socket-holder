package com.isthisone.socketholder.subscriber;

import com.isthisone.socketholder.channel.HolderChannel;

import java.util.Set;

public interface Subscriber<T> {

    void subscribe(T key, HolderChannel channel);

    void unsubscribe(HolderChannel channel);

    void unsubscribe(T key, HolderChannel channel);

    Set<String> get(T k);

    Set<String> getAll();

    boolean exist(T k);

    boolean exist(HolderChannel channel);

    boolean exist(T key, HolderChannel channel);

}
