package com.samton.web;


import com.samton.server.NettyChannelMap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import msg.TSPPushMsg;

/**
 * Created with IntelliJ IDEA.
 * 作者: duyingfeng
 * 日期: 17-9-18
 * 说明:
 * 与web端交互
 */
public class NettyWebServerBootstrap {


    private static final InternalLogger log = InternalLoggerFactory.getInstance(NettyWebServerBootstrap.class);

    /*是否使用https协议*/
    static final boolean SSL = System.getProperty("ssl") != null;

    static final int PORT = Integer.parseInt(System.getProperty("port", SSL ? "8443" : "6789"));

    static SslContext sslCtx = null;

    private int port;


    public NettyWebServerBootstrap(int port) throws InterruptedException {
        this.port = port;
        bind();
    }

    public void bind() throws InterruptedException {
        // Configure SSL.
        try {
            if (SSL) {
                SelfSignedCertificate ssc = new SelfSignedCertificate();
                sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
            } else {
                sslCtx = null;
            }
        } catch (Exception e) {
            log.info(e);
        }


        // Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);

            bootstrap.handler(new LoggingHandler(LogLevel.INFO));

            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {

                    ChannelPipeline p = ch.pipeline();

                    if (sslCtx != null) {
                        p.addLast(sslCtx.newHandler(ch.alloc()));
                    }

                    p.addLast(new HttpServerCodec());/*HTTP 服务的解码器*/

                    p.addLast(new HttpObjectAggregator(2048));/*HTTP 消息的合并处理*/

                    p.addLast(new HealthServerHandler()); /*自己写的服务器逻辑处理*/
                }
            });

            Channel ch = bootstrap.bind(PORT).sync().channel();

            System.err.println("Open your web browser and navigate to " +
                    (SSL ? "https" : "http") + "://192.168.0.91:" + PORT + '/');

            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }




    public static void main(String[] args) throws Exception {
        NettyWebServerBootstrap nwbs = new NettyWebServerBootstrap(PORT);

    }
}
