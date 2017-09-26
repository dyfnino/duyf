package com.samton.server;

import com.samton.util.DocumentCommun;
import com.samton.util.LogProducerUtils;
import com.samton.util.RedisUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import msg.TSPLdleCallPolice;
import org.bson.Document;


import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * 作者: duyingfeng
 * 日期: 17-9-12
 * 说明:
 * To change this template use File | Settings | File Templates.
 */
public class TSPLdleCallPoliceTread implements Runnable {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(TSPLdleCallPoliceTread.class);
    private TSPLdleCallPolice ldleCall;
    private RedisUtil redis;
    private LogProducerUtils kafk;
    private LinkedList lists;

    public TSPLdleCallPoliceTread(LinkedList lists, RedisUtil redis, LogProducerUtils kafk) {
        this.kafk = kafk;
        this.lists = lists;
        // this.ldleCall = ldleCall;
        this.redis = redis;
    }


    public void run() {

        for (int i = 0; i < lists.size(); i++) {
            System.out.println("====进入怠速报警线程====");
            log.info("====进入怠速报警线程====");
            ldleCall = (TSPLdleCallPolice) lists.get(i);

            String vin = "";
            String uuid = "";
            if (redis.hmget("carEquipment", ldleCall.getTermCode()).get(0) != null) {
                vin = redis.hmget("carEquipment", ldleCall.getTermCode()).get(0).toString().split("car_card=")[1].split("}")[0];
                if (redis.hmget("carvinuuid", vin) != null) {
                    uuid = redis.hmget("carvinuuid", vin).get(0).toString();

                    ldleCall.setVinCodee(vin);
                    ldleCall.setUuiduuid(uuid);

                    Document doc = DocumentCommun.LdleCallPolice2Doc(ldleCall);
                    System.out.println("怠速报警====" + doc.toJson());
                    kafk.send("alarmInfo", doc.toJson());
                    log.info("怠速报警====" + doc.toJson());
                }
            }

        }
    }
}
