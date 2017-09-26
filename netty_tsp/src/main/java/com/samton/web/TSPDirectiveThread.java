package com.samton.web;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslContext;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import msg.TSPPushMsg;

/**
 * Created with IntelliJ IDEA.
 * 作者: duyingfeng
 * 日期: 17-9-19
 * 说明:
 */
public class TSPDirectiveThread implements Runnable {


    private static final InternalLogger log = InternalLoggerFactory.getInstance(TSPDirectiveThread.class);

    private FullHttpRequest req;

    private JSONObject requestJson;

    private TSPPushMsg tspPushMsg;

    private JSONObject responseJson;

    public TSPDirectiveThread(JSONObject responseJson, FullHttpRequest req, JSONObject requestJson, TSPPushMsg tspPushMsg) {
        this.responseJson = responseJson;
        this.req = req;
        this.requestJson = requestJson;
        this.tspPushMsg = tspPushMsg;
    }

    @Override
    public void run() {

        //把客户端发送来的数据格式化为Json对象

        requestJson = JSONObject.parseObject(parseJosnRequest(req));

        System.out.println("vincode==="+requestJson.getString("vincode"));

        tspPushMsg.setVincode(requestJson.getString("vincode"));

        tspPushMsg.setClientId(requestJson.getString("vincode"));

        if (requestJson.getString("on_off") != null && requestJson.getString("on_ff") != "") {
            System.out.println("on_off=="+requestJson.getString("on_off"));
            if(requestJson.getString("on_off").equals("on")){

                tspPushMsg.setOn_off(requestJson.getString("on_off"));

            }else if(requestJson.getString("on_off").equals("off")){

                tspPushMsg.setOn_off(requestJson.getString("on_off"));
            }

        }
        log.info("接收web端的字符串" + requestJson);

        //获取客户端的URL
        log.info("获取客户端的URL"+req.uri());
        //根据不同的请求API做不同的处理(路由分发)，只处理POST方法
        if (req.method() == HttpMethod.POST) {
            if (req.uri().equals("/netty")) {

                responseJson.put("ok", "200");

            } else {
                //错误处理
                responseJson.put("error", "404 Not Find");
            }

        } else {
            //错误处理
            responseJson.put("error", "404 Not Find");
        }

    }

    public String parseJosnRequest(FullHttpRequest request) {
        ByteBuf jsonBuf = request.content();
        System.out.println(request.touch());
        String jsonStr = jsonBuf.toString(CharsetUtil.UTF_8);
        log.info("接收web端的字符串" + jsonStr);
        System.out.println("客户端发送的内容" + jsonStr);
        return jsonStr;
    }

}
