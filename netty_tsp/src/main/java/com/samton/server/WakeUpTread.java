package com.samton.server;

import com.samton.util.DocumentCommun;
import com.samton.util.LogProducerUtils;
import com.samton.util.RedisUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import msg.TSPWaitWakeUp;
import org.bson.Document;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * 作者: duyingfeng
 * 日期: 17-9-12
 * 说明:
 * To change this template use File | Settings | File Templates.
 */
public class WakeUpTread implements Runnable {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(WakeUpTread.class);
    private TSPWaitWakeUp wakeUp;
    private RedisUtil redis;
    private LogProducerUtils kafk;
    private LinkedList lists;

    public WakeUpTread(LinkedList lists,RedisUtil redis, LogProducerUtils kafk) {
        this.lists = lists;
      //  this.wakeUp = wakeUp;
        this.redis = redis;
        this.kafk = kafk;
    }

    public void run() {

        for (int i = 0; i < lists.size(); i++) {

            wakeUp = (TSPWaitWakeUp) lists.get(i);

            String vin = "";
            String uuid = "";

            long start = System.currentTimeMillis();
            if (redis.hmget("carEquipment", wakeUp.getTermCode()).get(0) != null) {
                long end = System.currentTimeMillis();
                System.out.println("wakeUp查询redis时间======2======" + (end - start) + " =SUCCESS++++++++++++");
                vin = redis.hmget("carEquipment", wakeUp.getTermCode()).get(0).toString().split("car_card=")[1].split("}")[0];
                if (redis.hmget("carvinuuid", vin) != null) {
                    uuid = redis.hmget("carvinuuid", vin).get(0).toString();

                    System.out.println("开机自唤醒的" + uuid);
                    wakeUp.setVinCodee(vin);
                    wakeUp.setUuiduuid(uuid);
                    wakeUp.setVoltagee(0.0);

                    Document doc = DocumentCommun.getWakeup2Doc(wakeUp);
                    long start1 = System.currentTimeMillis();

                    kafk.send("wakeUpInfo", doc.toJson());
                    log.info("wakeUpInfo发送kafk之前的输出" + doc.toJson());
                    long end1 = System.currentTimeMillis();

                }
            }

        }
    }
}