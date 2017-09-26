package com.samton.server;

import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * 作者: duyingfeng
 * 日期: 17-8-31
 * 说明:
 * To change this template use File | Settings | File Templates.
 */
public class NettyChannelMap {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(NettyChannelMap.class);
    private static Map<String,SocketChannel> map=new ConcurrentHashMap<String, SocketChannel>();

    public static void add(String clientId,SocketChannel socketChannel){

        map.put(clientId,socketChannel);

    }

    public static Channel get(String clientId){

        return map.get(clientId);

    }

    public static void remove(SocketChannel socketChannel){

        for (Map.Entry entry:map.entrySet()){

            if (entry.getValue()==socketChannel){

                map.remove(entry.getKey());
                log.debug("移除了channel==========="+entry.getKey());

            }

        }

    }
    public static List<SocketChannel> getAll() {
        List<SocketChannel> channelList = new ArrayList();
        for (Map.Entry entry : map.entrySet()) {
            channelList.add((SocketChannel) entry.getValue());
        }
        return channelList;
    }

}
