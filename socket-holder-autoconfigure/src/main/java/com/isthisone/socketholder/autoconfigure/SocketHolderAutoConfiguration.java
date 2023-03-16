package com.isthisone.socketholder.autoconfigure;

import com.isthisone.socketholder.autoconfigure.socket.SocketHandler;
import com.isthisone.socketholder.autoconfigure.socket.embedded.NettyServerBootstrap;
import com.isthisone.socketholder.autoconfigure.properties.SocketHolderProperties;
import com.isthisone.socketholder.autoconfigure.socket.SocketServerBootstrap;
import com.isthisone.socketholder.autoconfigure.subscriber.StringSubscriberNotify;
import com.isthisone.socketholder.broadcast.client.DefaultSocketHolderClient;
import com.isthisone.socketholder.broadcast.client.SocketHolderClient;
import com.isthisone.socketholder.broadcast.redis.client.RedisCaller;
import com.isthisone.socketholder.broadcast.redis.listener.RedisBroadcastListener;
import com.isthisone.socketholder.core.ListenableSocketHolder;
import com.isthisone.socketholder.core.SocketHolder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.lang.Nullable;

import java.util.List;

@Configuration(proxyBeanMethods = false)
@Import(SocketServerInitializerConfiguration.class)
@ConditionalOnBean(SocketHolderMarkerConfiguration.Marker.class)
@EnableConfigurationProperties(SocketHolderProperties.class)
public class SocketHolderAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ListenableSocketHolder socketHolder() {

        return new ListenableSocketHolder();
    }

    @Bean(destroyMethod = "destroy")
    @ConditionalOnBean(ListenableSocketHolder.class)
    public StringSubscriberNotify stringSubscriberNotify(ListenableSocketHolder listenableSocketHolder) {

        return new StringSubscriberNotify(listenableSocketHolder);
    }

    @Bean
    @ConditionalOnMissingBean
    public SocketServerBootstrap socketServerBootstrap(
            SocketHolderProperties socketHolderProperties, @Nullable List<SocketHandler> socketHandlers,
            SocketHolder socketHolder) {

        SocketServerBootstrap bootstrap = new NettyServerBootstrap(socketHolderProperties.getPort(), socketHolderProperties.getPath());
        if (socketHandlers != null) {
            if (socketHandlers.size() > 1) {
                throw new IllegalStateException("SocketHandler need 1 but found " + socketHandlers.size());
            }
            bootstrap.setSocketHandler(socketHandlers.get(0));
        }
        bootstrap.setSocketHolder(socketHolder);
        return bootstrap;
    }

    @Bean
    @ConditionalOnMissingBean
    public SocketHolderClient socketHolderClient(StringRedisTemplate stringRedisTemplate) {

        return new DefaultSocketHolderClient(new RedisCaller(stringRedisTemplate));
    }

    @Bean
    @ConditionalOnBean(RedisBroadcastListener.class)
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory connectionFactory, @Nullable List<RedisBroadcastListener> listeners) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        if (listeners != null) {
            for (RedisBroadcastListener listener : listeners) {
                container.addMessageListener(listeners.get(0), ChannelTopic.of(listener.topic()));
            }
        }
        return container;
    }

}
