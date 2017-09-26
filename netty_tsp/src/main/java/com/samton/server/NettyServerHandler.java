package com.samton.server;

import com.samton.util.LogProducerUtils;
import com.samton.util.RedisUtil;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.ReferenceCountUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import msg.*;

import java.lang.String;
import java.util.LinkedList;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * 作者: duyingfeng
 * 日期: 17-8-31
 * 说明:
 * T
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<BaseMsg> {


    private static final InternalLogger log = InternalLoggerFactory.getInstance(NettyServerHandler.class);

    private GpsThread gpsThread = null;

    private WakeUpTread wakeUpTread = null;

    private TSPSpeedUpTread tspSpeedUpTread = null;

    private TSPSharpBendThread tspSharpBendThread = null;

    private TSPSharpSlowBownTread tspSharpSlowBownTread = null;

    private TSPCallPoliceTread tspCallPoliceTread = null;

    private TSPFatigueDrivingTread tspFatigueDrivingTread = null;

    private TSPLdleCallPoliceTread tspLdleCallPoliceTread = null;

    private TSPConDrivingPoliThread tspConDrivingPoliThread = null;

    private ThreadPoolExecutor threadPoolExecutor = ThreadPoolExecutor.getInstance();

    private TSPPushMsg  tspPushMsg;

    public NettyServerHandler(LogProducerUtils kafk, RedisUtil redis) {
        this.kafk = kafk;
        this.redis = redis;
    }


    private LogProducerUtils kafk;

    private RedisUtil redis;


    public RedisUtil getRedis() {
        return redis;
    }

    public void setRedis(RedisUtil redis) {
        this.redis = redis;
    }

    public LogProducerUtils getKafk() {
        return kafk;
    }

    public void setKafk(LogProducerUtils kafk) {
        this.kafk = kafk;
    }



    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //channel失效，从Map中移除
        if (! ctx.channel().isActive()) {
            NettyChannelMap.remove((SocketChannel) ctx.channel());
            log.debug("移除了channel" + (SocketChannel) ctx.channel());
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.debug("错误原因：" + cause.getMessage());
        ctx.fireExceptionCaught(cause);
        if (! ctx.channel().isActive()) {
            ctx.channel().disconnect();
            log.debug(ctx.channel().remoteAddress() + "捕捉到的异常");
        }
        ctx.close();
        cause.printStackTrace();
        log.debug(cause);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        TSPPingMsg pingMsg = new TSPPingMsg();

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                ctx.channel().close();
                log.debug("通道关闭=====");
            } else if (e.state() == IdleState.WRITER_IDLE) {
                ctx.writeAndFlush(new TSPPingMsg());
            }
        }

    }




    /*
    类型匹配
    *
    *      TSPSHARPBEND,  //急转弯事件
           TSPSHARPSLOWbOWN, //  急减速事件
           TSPSPEEDUP,       //急加速事件
           TSPDRIVING,      //疲劳驾驶
           TSPSPEEDPOLICE,  //超速报警
           TSPLDLE, //怠速报警
           TSPCONDRIVING,//连续驾驶
           TSPFENCE      //电子围栏
    *    messageReceived接收客户端的数据
    * */


    protected void channelRead0(ChannelHandlerContext ctx, BaseMsg msg) throws Exception {

        TSPReplyMsg rep = new TSPReplyMsg();

        MsgListBean mb = (MsgListBean) msg;
        //推送web端的指令
        pushMsg(msg,ctx);

        switch (mb.getType()) {

            case TSPLOGIN: { //GPS

                LinkedList lists = mb.getMsgLinkedList();

                if (NettyChannelMap.get(mb.getClientId()) == null) {
                    NettyChannelMap.add(mb.getClientId(), (SocketChannel) ctx.channel());
                }
                log.info("channel连接数==" + NettyChannelMap.getAll().size());
                System.out.println("channel连接数GPS====" + NettyChannelMap.getAll().size());
                rep.setId(mb.getId());
                rep.setType(mb.getType());
                NettyChannelMap.get(mb.getClientId()).writeAndFlush(rep);
                log.debug(mb.getClientId() + "======" + rep + "========");

               /*
               * 使用线程池开启线程处理任务
               * */

                gpsThread = new GpsThread(lists, redis, kafk);
                threadPoolExecutor.execute(gpsThread);


            }
            break;
            case TSPWAKEUP: { //待机自换醒
                //  TSPWaitWakeUp wakeUp = (TSPWaitWakeUp) msg;
                LinkedList lists = mb.getMsgLinkedList();

                if (NettyChannelMap.get(mb.getClientId()) == null) {
                    NettyChannelMap.add(mb.getClientId(), (SocketChannel) ctx.channel());
                }
                System.out.println("channel连接数待机自换醒" + NettyChannelMap.getAll().size());
                log.info("channel连接数" + NettyChannelMap.getAll().size());
                rep.setId(mb.getId());
                rep.setType(mb.getType());
                NettyChannelMap.get(mb.getClientId()).writeAndFlush(rep);

                /*
                * 线程池
                * */


                wakeUpTread = new WakeUpTread(lists, redis, kafk);

                threadPoolExecutor.execute(wakeUpTread);


            }
            break;
            case TSPSHARPBEND: {   //急转弯事件
                //  TSPSharpBend sharpBend = (TSPSharpBend) msg;
                LinkedList lists = mb.getMsgLinkedList();
                if (NettyChannelMap.get(mb.getClientId()) == null) {
                    NettyChannelMap.add(mb.getClientId(), (SocketChannel) ctx.channel());
                }
                rep.setId(mb.getId());
                rep.setType(mb.getType());
                NettyChannelMap.get(mb.getClientId()).writeAndFlush(rep);

                tspSharpBendThread = new TSPSharpBendThread(lists, redis, kafk);
                threadPoolExecutor.execute(tspSharpBendThread);


            }
            break;
            case TSPSLOWBOWN: { //  急减速事件

                //  TSPSharpSlowBown slowBown = (TSPSharpSlowBown) msg;
                LinkedList lists = mb.getMsgLinkedList();
                if (NettyChannelMap.get(mb.getClientId()) == null) {
                    NettyChannelMap.add(mb.getClientId(), (SocketChannel) ctx.channel());
                }

                rep.setId(mb.getId());
                rep.setType(mb.getType());

                NettyChannelMap.get(mb.getClientId()).writeAndFlush(rep);


                tspSharpSlowBownTread = new TSPSharpSlowBownTread(lists, redis, kafk);
                threadPoolExecutor.execute(tspSharpSlowBownTread);

            }
            break;
            case TSPSPEEDUP: { //急加速事件

                //  TSPSpeedUp speedUp = (TSPSpeedUp) msg;

                LinkedList lists = mb.getMsgLinkedList();
                //getchannel(mb,ctx,rep);

                if (NettyChannelMap.get(mb.getClientId()) == null) {
                    NettyChannelMap.add(mb.getClientId(), (SocketChannel) ctx.channel());
                }
                rep.setId(mb.getId());
                rep.setType(mb.getType());
                NettyChannelMap.get(mb.getClientId()).writeAndFlush(rep);


                tspSpeedUpTread = new TSPSpeedUpTread(lists, redis, kafk);
                threadPoolExecutor.execute(tspSpeedUpTread);


            }
            break;
            case TSPDRIVING: { //疲劳驾驶

                // TSPFatigueDriving fatigueDriving = (TSPFatigueDriving) msg;
                LinkedList lists = mb.getMsgLinkedList();
                if (NettyChannelMap.get(mb.getClientId()) == null) {
                    NettyChannelMap.add(mb.getClientId(), (SocketChannel) ctx.channel());
                }
                rep.setId(mb.getId());
                rep.setType(mb.getType());
                NettyChannelMap.get(mb.getClientId()).writeAndFlush(rep);


                tspFatigueDrivingTread = new TSPFatigueDrivingTread(lists, redis, kafk);
                threadPoolExecutor.execute(tspFatigueDrivingTread);


            }
            break;
            case TSPSPEEDPOLICE: { //超速报警
                // TSPCallPolice speedPolice = (TSPCallPolice) msg;

                LinkedList lists = mb.getMsgLinkedList();
                if (NettyChannelMap.get(mb.getClientId()) == null) {
                    NettyChannelMap.add(mb.getClientId(), (SocketChannel) ctx.channel());
                }
                rep.setId(mb.getId());
                rep.setType(mb.getType());
                NettyChannelMap.get(mb.getClientId()).writeAndFlush(rep);


                tspCallPoliceTread = new TSPCallPoliceTread(lists, redis, kafk);
                threadPoolExecutor.execute(tspCallPoliceTread);


            }
            break;
            case TSPLDLE: { //怠速报警
                // TSPLdleCallPolice ldleCall = (TSPLdleCallPolice) msg;
                LinkedList lists = mb.getMsgLinkedList();
                if (NettyChannelMap.get(mb.getClientId()) == null) {
                    NettyChannelMap.add(mb.getClientId(), (SocketChannel) ctx.channel());
                }
                rep.setId(mb.getId());
                rep.setType(mb.getType());
                NettyChannelMap.get(mb.getClientId()).writeAndFlush(rep);


                tspLdleCallPoliceTread = new TSPLdleCallPoliceTread(lists, redis, kafk);
                threadPoolExecutor.execute(tspLdleCallPoliceTread);


            }
            break;
            case TSPCONDRIVING: { //持续驾驶报警
                //TSPContinuousDriving continDriving = (TSPContinuousDriving) msg;
                LinkedList lists = mb.getMsgLinkedList();
                if (NettyChannelMap.get(mb.getClientId()) == null) {
                    NettyChannelMap.add(mb.getClientId(), (SocketChannel) ctx.channel());
                }
                rep.setId(mb.getId());
                rep.setType(mb.getType());
                NettyChannelMap.get(mb.getClientId()).writeAndFlush(rep);

                tspConDrivingPoliThread = new TSPConDrivingPoliThread(lists, redis, kafk);
                threadPoolExecutor.execute(tspConDrivingPoliThread);


            }
            break;
            case TSPFENCE: { //电子围栏
                /*TSPFence fence = (TSPFence) msg;
                if (NettyChannelMap.get(fence.getClientId()) == null) {
                    NettyChannelMap.add(fence.getClientId(), (SocketChannel) ctx.channel());
                }
                rep.setId(mb.getId());
                rep.setType(mb.getType());
                NettyChannelMap.get(fence.getClientId()).writeAndFlush(rep);

                String key = fence.getTermCode();
                String vin = "";
                String uuid = "";
                long start = System.currentTimeMillis();
                if (redis.hmget("carEquipment", key).get(0) != null) {
                    vin = redis.hmget("carEquipment", key).get(0).split("car_card=")[1].split("}")[0];
                    if (redis.hmget("carvinuuid", vin) != null) {
                        uuid = redis.hmget("carvinuuid", vin).get(0);

                        fence.setVinCodee(vin);
                        fence.setUuiduuid(uuid);

                        Document doc = DocumentCommun.Fence2Doc(fence);

                        kafk.send("alarmInfo", doc.toJson());
                    }
                }
                long end = System.currentTimeMillis();
                System.out.println("===========接收到电子围栏的数据======10=======" + (end - start) + "=SUCCESS++++++++++++");*/
            }
            break;
            case TSPCONFIGURE: {
                LinkedList lists = mb.getMsgLinkedList();

                TSPConfigure config = null;

                if (NettyChannelMap.get(mb.getClientId()) == null) {
                    NettyChannelMap.add(mb.getClientId(), (SocketChannel) ctx.channel());
                }
              /*rep.setId(mb.getId());
                rep.setType(mb.getType());
                NettyChannelMap.get(mb.getClientId()).writeAndFlush(rep);*/

               /*public String  tsp_host;    //ip 端口配置
                public int  idlingtime;     //怠速判断时间
                public double  idling_speed;//怠速判断值m/s
                public int  swerve;         //方向转弯判断值
                public int  gps_uprate;     //上传gs频率值
                public double  acceleration;//急加速度判断值m/s
                public double  deceleration;//急减速度判断值m/s
                public int  angular_velocity;//急转速度判断值m/s
                public double  instantaneous;//瞬时速度判断值m/s
                redis中的on-off=false 为不修改配置
                为true时修改配置文件
               */
                if (lists.get(0) != null) {
                    config = (TSPConfigure) lists.get(0);

                    String fig = redis.hmget("Termconfig", "config").get(0).toString();
                    System.out.println("查询===================" + fig);
                    System.out.println("开关打开的值===============" + fig.split(",")[9].split("on-off=")[1].split("}")[0]);
                    if (fig != null && fig.split(",")[9].split("on-off=")[1].split("}")[0].equals("true")) {
                        System.out.println("开关打开的值===============" + fig.split(",")[9].split("on-off=")[1].split("}")[0]);
                        config.setTsp_host(fig.split(",")[0].split("tsp_host=")[1]);

                        config.setIdlingtime(Integer.parseInt(fig.split(",")[1].split("idlingtime=")[1]));

                        config.setIdling_speed(Double.parseDouble(fig.split(",")[2].split("idling_speed=")[1]));

                        config.setSwerve(Integer.parseInt(fig.split(",")[3].split("swerve=")[1]));
                        System.out.println("swerve======================" + Integer.parseInt(fig.split(",")[3].split("swerve=")[1]));
                        config.setGps_uprate(Integer.parseInt(fig.split(",")[4].split("gps_uprate=")[1]));
                        System.out.println("gps_uprate======================" + Integer.parseInt(fig.split(",")[4].split("gps_uprate=")[1]));
                        config.setAcceleration(Double.parseDouble(fig.split(",")[5].split("acceleration=")[1]));

                        config.setDeceleration(Double.parseDouble(fig.split(",")[6].split("deceleration=")[1]));

                        config.setAngular_velocity(Integer.parseInt(fig.split(",")[7].split("angular_velocity=")[1]));

                        config.setInstantaneous(Double.parseDouble(fig.split(",")[8].split("instantaneous=")[1]));
                        //发送给客户端
                        NettyChannelMap.get(mb.getClientId()).writeAndFlush(config);

                        System.out.println("设置成功" + config.deceleration);
                    } else {
                        System.out.println("开关未打开不设置配置" + config.deceleration);
                    }
                }
            }
            break;
            default:break;
        }
        ReferenceCountUtil.release(msg);
    }

    public void pushMsg(BaseMsg mb,ChannelHandlerContext ctx){

        if(tspPushMsg.getOn_off().equals("on")||tspPushMsg.getOn_off().equals("off")){
            NettyChannelMap.get(tspPushMsg.getVincode()).writeAndFlush(tspPushMsg);
            tspPushMsg.setOn_off("");
        }
    }
}
