package tcp;

import enums.MsgType;

import msg.BaseMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import msg.TSPPingMsg;
import msg.TSPReplyMsg;


/**
 * Created with IntelliJ IDEA.
 * 作者: duyingfeng
 * 日期: 17-8-31
 * 说明:
 * To change this template use File | Settings | File Templates.
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<BaseMsg> {
    //利用写空闲发送心跳检测消息
    @Override

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {

            IdleStateEvent e = (IdleStateEvent) evt;

            switch (e.state()) {

                case WRITER_IDLE:

                    TSPPingMsg pingMsg=new TSPPingMsg();

                    ctx.writeAndFlush(pingMsg);

                    System.out.println("send ping to server----------");

                    break;

                default:

                    break;

            }

        }

    }



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseMsg msg) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
        MsgType msgType=msg.getType();
        System.out.println(msgType);

        switch (msgType){

            case TSPLOGIN:{
                //接收服务端发过来的消息

            }break;
            case TSPPING:{

                System.out.println("receive ping from server----------");

            }break;

            case TSPREPLY:{
                TSPReplyMsg rep=(TSPReplyMsg)msg;
                System.out.println(rep.getReply());

            }
            default:break;


        }

        ReferenceCountUtil.release(msgType);
    }
}
