package com.samton.kafka;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
/**
 * 参考http://blog.csdn.net/zhengyong15984285623/article/details/52054638
 * @author larry
 *
 */
public class MyConsumer {
	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("zookeeper.connect", "hadoopMaster:2181,hadoopSlave1:2181,hadoopSlave2:2181");
		props.put("bootstrap.servers", "hadoopMaster:9092,hadoopSlave1:9092,hadoopSlave2:9092");
		
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		//可以给消费者分组
		props.put("group.id", "tspData");
		//要不要再启动消费者 自动把offset重新设置。不设置可能会接着上一次的偏移量去读。
		//smallest指的是把offset设置为最小 最开始的偏移量   begining
		//props.put("auto.offset.reset", "smallest");
		
		KafkaConsumer<String,String> consumer = new KafkaConsumer<String,String>(props);
		/* 消费者订阅的topic, 可同时订阅多个 */
		consumer.subscribe(Arrays.asList("tspData"));
        while (true) {
            try {
				
            	ConsumerRecords<String, String> records = consumer.poll(100);
            	System.out.println("count"+records.count());
            	for (ConsumerRecord<String, String> record : records) {
            		Thread.sleep(1000l);
            		System.out.printf("offset = %d, key = %s, value = %s \n", record.offset(), record.key(), record.value());
            	}
			} catch (Exception e) {
				e.printStackTrace();
			}
            
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
		
	}
}
