package com.isthisone.socketholder.channel;

public interface HolderChannel {

    String getId();

    Object attr(String key);

    void attr(String key, Object val);

    boolean isOpen();

    void send(String msg);


}
