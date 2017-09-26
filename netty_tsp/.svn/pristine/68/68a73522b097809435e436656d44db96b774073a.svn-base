package com.samton.server;

import com.samton.util.DocumentCommun;
import com.samton.util.LogProducerUtils;
import com.samton.util.RedisUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import msg.TSPSharpBend;
import org.bson.Document;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * 作者: duyingfeng
 * 日期: 17-9-12
 * 说明:
 * To change this template use File | Settings | File Templates.
 */
public class TSPSharpBendThread implements Runnable {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(TSPSharpBendThread.class);
    private TSPSharpBend sharpBend;
    private RedisUtil redis;
    private LogProducerUtils kafk;
    private LinkedList lists;

    public TSPSharpBendThread(LinkedList lists, RedisUtil redis, LogProducerUtils kafk) {
        this.kafk = kafk;
        this.lists = lists;
        // this.sharpBend = sharpBend;
        this.redis = redis;
    }


    public void run() {

        for (int i = 0; i < lists.size(); i++) {

            sharpBend = (TSPSharpBend) lists.get(i);

            String vin = "";
            String uuid = "";

            long start = System.currentTimeMillis();

            if (redis.hmget("carEquipment", sharpBend.getTermCode()).get(0) != null) {

                vin = redis.hmget("carEquipment", sharpBend.getTermCode()).get(0).toString().split("car_card=")[1].split("}")[0];

                if (redis.hmget("carvinuuid", vin) != null) {
                    uuid = redis.hmget("carvinuuid", vin).get(0).toString();
                    sharpBend.setVinCodee(vin);
                    sharpBend.setUuiduuid(uuid);

                    Document doc = DocumentCommun.getSharpBend2Doc(sharpBend);
                    System.out.println("没发送之前急转弯事件kafk输出====" + doc.toJson());
                    kafk.send("drivingHabitInfo", doc.toJson());
                    log.debug("急转弯事件kafk输出====" + doc.toJson());
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("============接收到急转弯事件的数据========3====" + (end - start) + "=SUCCESS++++++++++++");
        }
    }
}
