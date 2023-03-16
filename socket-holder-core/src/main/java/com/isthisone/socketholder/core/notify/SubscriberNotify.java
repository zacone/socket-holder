package com.isthisone.socketholder.core.notify;

import java.util.Set;

public interface SubscriberNotify<T> {

    void notify(T key, String msg);

    void notifyAndExclude(T key, String msg, Set<String> socketIds);

    void notifyAll(String msg);

    void notifyAllAndExclude(String msg, Set<String> socketIds);

}
