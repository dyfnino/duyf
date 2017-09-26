package com.samton.web;


import com.alibaba.fastjson.JSONObject;
import com.samton.server.GpsThread;
import com.samton.server.NettyChannelMap;
import com.samton.server.ThreadPoolExecutor;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;
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
public class HealthServerHandler extends ChannelInboundHandlerAdapter {


    private static final InternalLogger log = InternalLoggerFactory.getInstance(HealthServerHandler.class);

    private static final AsciiString CONTENT_TYPE = new AsciiString("Content-Type");

    private static final AsciiString CONTENT_LENGTH = new AsciiString("Content-Length");

    private static final AsciiString CONNECTION = new AsciiString("Connection");

    private static final AsciiString KEEP_ALIVE = new AsciiString("keep-alive");

    private TSPPushMsg tspPushMsg = null;

    private JSONObject responseJson= null;

    private JSONObject requestJson= null;

    private FullHttpRequest req= null;

    private ThreadPoolExecutor threadPoolExecutor = ThreadPoolExecutor.getInstance();

    private TSPDirectiveThread tspDirectiveThread;

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        if (msg instanceof FullHttpRequest) {

            responseJson = new JSONObject();//新建一个返回消息的Json对象
            tspPushMsg=new TSPPushMsg();
            req = (FullHttpRequest) msg;//客户端的请求对象

            try {


                tspDirectiveThread = new TSPDirectiveThread(responseJson,req,requestJson,tspPushMsg);
                threadPoolExecutor.execute(tspDirectiveThread);

                /*requestJson = JSONObject.parseObject(parseJosnRequest(req));

                System.out.println("vincode===" + requestJson.getString("vincode"));

                tspPushMsg.setVincode(requestJson.getString("vincode"));

                tspPushMsg.setClientId(requestJson.getString("vincode"));

                if (requestJson.getString("on_off") != null && requestJson.getString("on_ff") != "") {
                    System.out.println("on_off==" + requestJson.getString("on_off"));
                    if (requestJson.getString("on_off").equals("on")) {

                        tspPushMsg.setOn_off(requestJson.getString("on_off"));

                    } else if (requestJson.getString("on_off").equals("off")) {

                        tspPushMsg.setOn_off(requestJson.getString("on_off"));
                    }

                }*/

            //    log.info("接收web端的字符串" + requestJson);

            } catch (Exception e) {
                ResponseJson(ctx, req, new String("error json"));
                //  return;
            }

          //  log.info(req.uri());//获取客户端的URL
          /*  System.out.println("获取客户端的URL" + req.uri());
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
            }*/

            //向客户端发送结果
            ResponseJson(ctx, req, responseJson.toString());
        }
    }


    /**
     * 响应HTTP的请求
     *
     * @param ctx
     * @param req
     * @param jsonStr
     */
    private void ResponseJson(ChannelHandlerContext ctx, FullHttpRequest req, String jsonStr) {

        boolean keepAlive = HttpUtil.isKeepAlive(req);
        byte[] jsonByteByte = jsonStr.getBytes();
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(jsonByteByte));
        response.headers().set(CONTENT_TYPE, "text/json");
        response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());

        if (! keepAlive) {
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            response.headers().set(CONNECTION, KEEP_ALIVE);
            ctx.write(response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 获取请求的内容
     *
     * @param request
     * @return
     */
    public String parseJosnRequest(FullHttpRequest request) {
        ByteBuf jsonBuf = request.content();
        System.out.println(request.touch());
        String jsonStr = jsonBuf.toString(CharsetUtil.UTF_8);
        log.info("接收web端的字符串" + jsonStr);
        System.out.println("客户端发送的内容" + jsonStr);
        return jsonStr;
    }

}
