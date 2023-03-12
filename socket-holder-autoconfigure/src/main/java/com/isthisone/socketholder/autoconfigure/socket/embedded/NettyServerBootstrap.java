package com.isthisone.socketholder.autoconfigure.socket.embedded;

import com.isthisone.socketholder.autoconfigure.socket.SocketHandler;
import com.isthisone.socketholder.autoconfigure.socket.SocketServerBootstrap;
import com.isthisone.socketholder.core.SocketHolder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

public class NettyServerBootstrap implements SocketServerBootstrap {

    private static final Logger logger = LoggerFactory.getLogger(NettyServerBootstrap.class);

    static final boolean SSL = System.getProperty("ssl") != null;

    private final int port;

    private final String path;

    private SocketHolder socketHolder;

    private SocketHandler socketHandler;

    public NettyServerBootstrap(int port, String path) {
        this.port = port;
        this.path = path;
    }

    @Override
    public void start() throws Exception {
        // Configure SSL.
        final SslContext sslCtx = buildSslContext();

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        WebSocketFrameHandler handler = new WebSocketFrameHandler(socketHolder, socketHandler);

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            if (sslCtx != null) {
                                pipeline.addLast(sslCtx.newHandler(ch.alloc()));
                            }
                            pipeline.addLast(new HttpServerCodec());
                            pipeline.addLast(new HttpObjectAggregator(65536));
                            pipeline.addLast(new WebSocketServerCompressionHandler());
                            pipeline.addLast(new WebSocketServerProtocolHandler(path, null, true));
                            pipeline.addLast(handler);
                        }
                    });

            Channel ch = b.bind(port).sync().channel();
            logger.info("Socket holder started on port(s): {} with path '{}'", port, path);
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private SslContext buildSslContext() throws CertificateException, SSLException {
        if (!SSL) {
            return null;
        }
        SelfSignedCertificate ssc = new SelfSignedCertificate();
        return SslContextBuilder
                .forServer(ssc.certificate(), ssc.privateKey())
                .build();
    }

    @Override
    public void setSocketHandler(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    @Override
    public void setSocketHolder(SocketHolder socketHolder) {
        this.socketHolder = socketHolder;
    }
}
