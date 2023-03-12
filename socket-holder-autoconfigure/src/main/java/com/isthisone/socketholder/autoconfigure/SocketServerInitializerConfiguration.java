package com.isthisone.socketholder.autoconfigure;

import com.isthisone.socketholder.autoconfigure.event.SocketServerStartedEvent;
import com.isthisone.socketholder.autoconfigure.socket.SocketServerBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration(proxyBeanMethods = false)
public class SocketServerInitializerConfiguration implements SmartLifecycle, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(SocketServerInitializerConfiguration.class);

    private final ApplicationContext applicationContext;

    private final SocketServerBootstrap socketServerBootstrap;

    public SocketServerInitializerConfiguration(ApplicationContext applicationContext, SocketServerBootstrap socketServerBootstrap) {
        this.applicationContext = applicationContext;
        this.socketServerBootstrap = socketServerBootstrap;
    }

    private boolean running;

    @Override
    public void start() {
        new Thread(() -> {
            try {
                socketServerBootstrap.start();

                SocketServerInitializerConfiguration.this.running = true;
                publish(new SocketServerStartedEvent());
            } catch (Exception e) {
                logger.error("Could not initialize Socket servlet", e);
            }
        }).start();
    }

    private void publish(ApplicationEvent event) {
        this.applicationContext.publishEvent(event);
    }

    @Override
    public void stop() {
        this.running = false;
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    @Override
    public int getPhase() {
        return 0;
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public int getOrder() {
        return 1;
    }

}
