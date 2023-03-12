package com.isthisone.socketholder.core.notify;

public interface SubscribeNotify<T> {

    void notify(T key, String msg);

}
