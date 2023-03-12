package com.isthisone.socketholder.channel;

public interface HolderChannel {
    String getId();

    boolean isOpen();

    void send(String msg);
}
