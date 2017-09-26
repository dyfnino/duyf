package com.samton.server;

import com.samton.util.DocumentCommun;
import com.samton.util.LogProducerUtils;
import com.samton.util.RedisUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import msg.TSPFatigueDriving;
import org.bson.Document;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * 作者: duyingfeng
 * 日期: 17-9-12
 * 说明:
 * To change this template use File | Settings | File Templates.
 */
public class TSPFatigueDrivingTread implements Runnable {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(TSPFatigueDrivingTread.class);
    private TSPFatigueDriving fatigueDriving;
    private RedisUtil redis;
    private LogProducerUtils kafk;
    private LinkedList lists;

    public TSPFatigueDrivingTread(LinkedList lists, RedisUtil redis, LogProducerUtils kafk) {
        this.kafk = kafk;
        this.lists = lists;
        // this.fatigueDriving = fatigueDriving;
        this.redis = redis;
    }


    public void run() {

        for (int i = 0; i < lists.size(); i++) {

            fatigueDriving = (TSPFatigueDriving) lists.get(i);

            String vin = "";
            String uuid = "";
            if (redis.hmget("carEquipment", fatigueDriving.getTermCode()).get(0) != null) {
                vin = redis.hmget("carEquipment", fatigueDriving.getTermCode()).get(0).toString().split("car_card=")[1].split("}")[0];
                if (redis.hmget("carvinuuid", vin) != null) {
                    uuid = redis.hmget("carvinuuid", vin).get(0).toString();

                    fatigueDriving.setVinCodee(vin);
                    fatigueDriving.setUuiduuid(uuid);

                    Document doc = DocumentCommun.FatigueDriving2Doc(fatigueDriving);

                    kafk.send("drivingHabitInfo", doc.toJson());
                    log.debug("疲劳驾驶输出====" + doc.toJson());
                }
            }


        }
    }
}
