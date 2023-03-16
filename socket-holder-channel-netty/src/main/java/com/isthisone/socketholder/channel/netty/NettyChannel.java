package com.isthisone.socketholder.channel.netty;

import com.isthisone.socketholder.channel.AbstractHolderChannel;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;

import java.util.Objects;

public class NettyChannel extends AbstractHolderChannel {

    private final Channel channel;

    public static NettyChannel of(Channel channel) {
        NettyChannel nettyChannel = (NettyChannel) channel
                .attr(AttributeKey.valueOf("channel")).get();
        if (nettyChannel != null) {
            return nettyChannel;
        }
        return new NettyChannel(channel);
    }

    private NettyChannel(Channel channel) {
        this.channel = channel;
        channel.attr(AttributeKey.valueOf("channel")).set(this);
    }

    @Override
    public String getId() {
        return channel.id().asLongText();
    }

    @Override
    public boolean isOpen() {
        return channel.isOpen();
    }

    @Override
    public void send(String msg) {
        channel.writeAndFlush(new TextWebSocketFrame(msg));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NettyChannel that = (NettyChannel) o;
        return Objects.equals(channel, that.channel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channel);
    }
}
