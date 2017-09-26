package tcp;

import enums.MsgType;
import msg.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * 作者: duyingfeng
 * 日期: 17-8-31
 * 说明:
 * To change this template use File | Settings | File Templates.
 */
public class NettyClientBootstrap {


    private int port;

    private String host;

    private SocketChannel socketChannel;

    private static final EventExecutorGroup group = new DefaultEventExecutorGroup(20);

    public NettyClientBootstrap(int port, String host) throws InterruptedException {

        this.port = port;

        this.host = host;

        start();

    }

    private void start() throws InterruptedException {

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.channel(NioSocketChannel.class);

        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);

        bootstrap.group(eventLoopGroup);

        bootstrap.remoteAddress(host, port);

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {

            @Override

            protected void initChannel(SocketChannel socketChannel) throws Exception {

                socketChannel.pipeline().addLast(new IdleStateHandler(20, 10, 0));

                socketChannel.pipeline().addLast(new ObjectEncoder());

                socketChannel.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));

                socketChannel.pipeline().addLast(new NettyClientHandler());

            }

        });

        ChannelFuture future = bootstrap.connect(host, port).sync();
        if (future.isSuccess()) {
            socketChannel = (SocketChannel) future.channel();
            System.out.println("connect server  成功---success------");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Date currentTime = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        MsgListBean mb = new MsgListBean();

        mb.setId(123456);

        mb.setType(MsgType.TSPLOGIN);

        mb.setClientId("358732057284420");

        LinkedList lists = new LinkedList();

        TSPGpsBasicInfoVo gpsBasicInfoVo = new TSPGpsBasicInfoVo();

        while (true) {

            Constants.setClientId("358732057284420");

            NettyClientBootstrap bootstrap = new NettyClientBootstrap(17479, "112.35.25.92");

            gpsBasicInfoVo.setVinCodee("B00002");

            gpsBasicInfoVo.setUuiduuid("ff8080815ea7667c015ea78bd1620036");

            gpsBasicInfoVo.setTermCode("358732057284420");

            gpsBasicInfoVo.setTimeStam(1506);

            gpsBasicInfoVo.setSpeedsss(13.999965443234247);
            //114.417918 37.999088

            gpsBasicInfoVo.setLatitude("+37.999088");

            gpsBasicInfoVo.setLongtude("+114.417918");

            gpsBasicInfoVo.setAltitude(-1.0);

            gpsBasicInfoVo.setDirector(238);

            gpsBasicInfoVo.setSatellit(8);

            gpsBasicInfoVo.setTempOnes(-1);

            gpsBasicInfoVo.setTempTwos(-1);

            gpsBasicInfoVo.setTempThre(-1);

            gpsBasicInfoVo.setNetStand("4");

            gpsBasicInfoVo.setSignalLv(5);

            gpsBasicInfoVo.setWifiStat(0);

            gpsBasicInfoVo.setAreaCode("110102");

            gpsBasicInfoVo.setDisplace(11.555517159149163);



            lists.add(gpsBasicInfoVo);

            mb.setMsgLinkedList(lists);

            bootstrap.socketChannel.writeAndFlush(mb);

            TimeUnit.SECONDS.sleep(5);

        }

    }
}
