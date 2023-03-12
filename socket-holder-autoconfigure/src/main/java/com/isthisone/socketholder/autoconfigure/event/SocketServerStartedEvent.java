package com.isthisone.socketholder.autoconfigure.event;

import org.springframework.context.ApplicationEvent;

public class SocketServerStartedEvent extends ApplicationEvent {
    public SocketServerStartedEvent() {
        super(new Object());
    }
}
