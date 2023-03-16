package com.isthisone.socketholder.autoconfigure.subscriber;

import com.isthisone.socketholder.core.ListenableSocketHolder;

public class StringSubscriberNotify extends MemoSubscriberNotify<String> {

    public StringSubscriberNotify(ListenableSocketHolder listenableSocketHolder) {
        super(listenableSocketHolder);
    }
}
