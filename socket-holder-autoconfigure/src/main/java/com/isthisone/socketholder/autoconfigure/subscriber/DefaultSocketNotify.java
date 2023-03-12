package com.isthisone.socketholder.autoconfigure.subscriber;

import com.isthisone.socketholder.core.ListenableSocketHolder;
import com.isthisone.socketholder.core.notify.SubscribeSocketNotify;
import com.isthisone.socketholder.subscriber.memo.MemoSubscriber;

public class DefaultSocketNotify extends SubscribeSocketNotify<String> {
    public DefaultSocketNotify(ListenableSocketHolder registry) {
        super(new MemoSubscriber<>(), registry);
    }
}
