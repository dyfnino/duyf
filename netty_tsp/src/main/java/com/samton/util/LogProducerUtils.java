package com.samton.util;
import java.util.Properties;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;


public class LogProducerUtils {
    private KafkaProducer<String,String> inner;
    private static final InternalLogger log = InternalLoggerFactory.getInstance(LogProducerUtils.class);
    public LogProducerUtils() throws Exception{
        if(inner==null){
            Properties props = new Properties();
            //zookeeper
            props.put("zk.connect", "zookeeper1:2181,zookeeper2:2181,zookeeper3:2181");
            props.put("bootstrap.servers", "kafka1:9092,kafka2:9092,kafka3:9092");

           /* props.put("zk.connect", "192.168.0.247:2181,192.168.0.247:2181,192.168.0.247:2181");
            props.put("bootstrap.servers", "192.168.0.247:9092,192.168.0.247:9092,192.168.0.247:9092");*/

            // 因为是要网络传输。所以要实现kafka的序列化接口
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            //防止丢失数据
            props.put("compression.type", "gzip");//消息压缩
            props.put("linger.ms", "50");// sleep 50ms 继续发送 将50变为数字试一次
            props.put("acks", "all");// 请求等待所有应答才算成功
            props.put("retries", 30);// 请求重试30
            props.put("reconnect.backoff.ms", 20000);// 重连时间
            props.put("retry.backoff.ms", 20000);// 重试时间
            inner = new KafkaProducer<String, String>(props);   
        }
    }
    public  void send(String topicName,String message) {
        if(topicName == null || message == null){
            log.debug("输出kafa报文"+message);
            log.debug("kafk调用成功");
            return;
        }
        ProducerRecord<String, String> km = new ProducerRecord<String, String>(topicName,message);
        inner.send(km);
    }
    public void close(){
        if(inner!=null){
            inner.close();   
        }
    }
}