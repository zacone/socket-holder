package com.isthisone.socketholder.autoconfigure.subscriber;

import com.isthisone.socketholder.core.ListenableSocketHolder;
import com.isthisone.socketholder.core.notify.DefaultSubscriberNotify;
import com.isthisone.socketholder.subscriber.memo.MemoSubscriber;

public class MemoSubscriberNotify<T> extends DefaultSubscriberNotify<T> {

    public MemoSubscriberNotify(ListenableSocketHolder listenableSocketHolder) {
        super(new MemoSubscriber<>(), listenableSocketHolder);
    }
}
