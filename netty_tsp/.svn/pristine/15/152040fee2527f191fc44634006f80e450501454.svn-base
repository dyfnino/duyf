package com.samton.server;

import com.samton.util.DocumentCommun;
import com.samton.util.LogProducerUtils;
import com.samton.util.RedisUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import msg.TSPSharpSlowBown;
import org.bson.Document;


import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * 作者: duyingfeng
 * 日期: 17-9-12
 * 说明:
 * To change this template use File | Settings | File Templates.
 */
public class TSPSharpSlowBownTread implements Runnable {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(TSPSharpSlowBownTread.class);
    private TSPSharpSlowBown slowBown;
    private RedisUtil redis;
    private LogProducerUtils kafk;
    private LinkedList lists;

    public TSPSharpSlowBownTread(LinkedList lists, RedisUtil redis, LogProducerUtils kafk) {
        this.kafk = kafk;
        this.lists = lists;
        // this.slowBown = slowBown;
        this.redis = redis;
    }


    public void run() {

        for (int i = 0; i < lists.size(); i++) {

            slowBown = (TSPSharpSlowBown) lists.get(i);

            String vin = "";
            String uuid = "";
            if (redis.hmget("carEquipment", slowBown.getTermCode()).get(0) != null) {
                vin = redis.hmget("carEquipment", slowBown.getTermCode()).get(0).toString().split("car_card=")[1].split("}")[0];
                if (redis.hmget("carvinuuid", vin) != null) {
                    uuid = redis.hmget("carvinuuid", vin).get(0).toString();

                    slowBown.setVinCodee(vin);
                    slowBown.setUuiduuid(uuid);

                    Document doc = DocumentCommun.getSpeeddown2Doc(slowBown);
                    System.out.println("没发送之前急减速事kfka件输出====" + doc.toJson());
                    kafk.send("drivingHabitInfo", doc.toJson());
                    log.debug("急减速事kfka件输出====" + doc.toJson());
                }
            }


        }
    }
}
