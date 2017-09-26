package com.samton.server;

import com.samton.util.DocumentCommun;
import com.samton.util.LogProducerUtils;
import com.samton.util.RedisUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import msg.TSPGpsBasicInfoVo;
import org.bson.Document;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * 作者: duyingfeng
 * 日期: 17-9-7
 * 说明:
 * To change this template use File | Settings | File Templates.
 */
public class GpsThread implements Runnable {


    private static final InternalLogger log = InternalLoggerFactory.getInstance(GpsThread.class);

    private TSPGpsBasicInfoVo gps;

    private LogProducerUtils kafk;

    private LinkedList lists;

    private RedisUtil redis;

    public GpsThread(LinkedList lists, RedisUtil redis, LogProducerUtils kafk) {
        // this.gps = gps;
        this.redis = redis;
        this.kafk = kafk;
        this.lists = lists;

    }

    // TSPGpsBasicInfoVo

    public void run() {
          log.debug("进入GPS线程");
        for (int i = 0; i < lists.size(); i++) {
            log.debug("进入GPSlists的大小===="+lists.size());
            gps = (TSPGpsBasicInfoVo) lists.get(i);

            String vin = "";
            String uuid = "";
            long start = System.currentTimeMillis();

            if (redis.hmget("carEquipment", gps.getTermCode()).get(0) != null) {

                long end = System.currentTimeMillis();
                System.out.println("======查询redis判断时间========" + (end - start) + "============");
                vin = redis.hmget("carEquipment", gps.getTermCode()).get(0).toString().split("car_card=")[1].split("}")[0];
                log.info("vin======GPS===" + vin);
                if (redis.hmget("carvinuuid", vin) != null) {
                    uuid = redis.hmget("carvinuuid", vin).get(0).toString();
                    log.info("GPS=======uuid=========" + uuid);
                    gps.setVinCodee(vin);
                    gps.setUuiduuid(uuid);
                    gps.setTempOnes(- 1L);
                    gps.setTempTwos(- 1L);
                    gps.setTempThre(- 1L);
                    gps.setWifiStat(0);
                    gps.setSignalLv(5);
                    gps.setAltitude(- 1.0);//
                    Document doc = DocumentCommun.GisVo2Doc(gps);
                    log.info("gisInfo没有发送kafk之前的输出" + doc.toJson());
                    kafk.send("gisInfo", doc.toJson());
                    log.debug("GPS的输出" + doc.toJson());
                }
            }

        }
    }


}



