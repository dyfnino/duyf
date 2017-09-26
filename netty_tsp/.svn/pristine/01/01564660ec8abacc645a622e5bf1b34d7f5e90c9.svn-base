package com.samton.kafka;

import java.util.Date;
import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;


public class CloudProducer {
	public static void main(String[] args) throws InterruptedException {
		Properties props = new Properties();
		props.put("zk.connect", "Mongo1:2181,Mongo2:2181,Mongo3:2181");
		props.put("bootstrap.servers", "Kafka1:9092,Kafka2:9092,Kafka3:9092");
		//因为是要网络传输。所以要实现kafka的序列化接口
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		props.put("compression.type", "gzip");
		props.put("linger.ms", "50");//sleep 50ms 继续发送  将50变为数字试一次
		props.put("acks", "all");//请求等待所有应答才算成功
		props.put("retries", 30);//请求重试30
		props.put("reconnect.backoff.ms", 20000);//重连时间
		props.put("retry.backoff.ms", 20000);//重试时间
		
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
		
		try {
			for(int i =new Integer(args[0]).intValue();i<=new Integer(args[1]).intValue();i++){
				//Thread.sleep(10000);
				///type:1,terminal:7x17230a,timestmp=1879668997,x:9.0,y:87.6
				producer.send(new ProducerRecord<String, String>("tspData", "type:1,terminal:7x17230a"+i+",timestamp:"+new Date().getTime()+",x:0.98,y:0.88,id:"+i), new Callback(){
				/**
				 * <1> 若指定Partition ID,则PR被发送至指定Partition
					<2> 若未指定Partition ID,但指定了Key, PR会按照hasy(key)发送至对应Partition
					<3> 若既未指定Partition ID也没指定Key，PR会按照round-robin模式发送到每个Partition
					<4> 若同时指定了Partition ID和Key, PR只会发送到指定的Partition (Key不起作用，代码逻辑决定)	
				 */
					
//			producer.send(new ProducerRecord<String, String>("tspData", partition, key, value), new Callback(){

					public void onCompletion(RecordMetadata metadata, Exception exception) {
						
					}
					
				});
				if(i%10000 == 0){
					System.out.println("-------------------------------------------------------i="+i);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
