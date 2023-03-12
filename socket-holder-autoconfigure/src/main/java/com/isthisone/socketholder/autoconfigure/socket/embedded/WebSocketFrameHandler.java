package com.isthisone.socketholder.autoconfigure.socket.embedded;

import com.isthisone.socketholder.autoconfigure.socket.SocketHandler;
import com.isthisone.socketholder.channel.netty.NettyChannel;
import com.isthisone.socketholder.core.SocketHolder;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

@ChannelHandler.Sharable
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final SocketHolder socketHolder;

    private final SocketHandler socketHandler;

    public WebSocketFrameHandler(SocketHolder socketHolder, SocketHandler socketHandler) {
        this.socketHolder = socketHolder;
        this.socketHandler = socketHandler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
        if (socketHandler != null) {
            socketHandler.process(NettyChannel.of(ctx.channel()), frame.text());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        if (socketHolder != null) {
            socketHolder.register(NettyChannel.of(ctx.channel()));
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (socketHolder != null) {
            socketHolder.unregister(NettyChannel.of(ctx.channel()));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (socketHolder != null) {
            socketHolder.unregister(NettyChannel.of(ctx.channel()));
        }
    }
}
