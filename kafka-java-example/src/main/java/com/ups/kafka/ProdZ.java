package com.ups.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProdZ {

  public static void main(String[] args) {
    Properties props = new Properties();
    props.put("bootstrap.servers", "127.0.0.1:9092");
    props.put("acks", "1");
    props.put("retries", 0);
    props.put("batch.size", 2048);
    props.put("linger.ms", 1);
    props.put("buffer.memory", 4194304);
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    Producer<String, String> producer = null;
    try {
      producer = new KafkaProducer<>(props);
      for (int i = 0; i < 100; i++) {
        String msg = "50,200," + i + "#1Z00999000"+i;
        producer.send(new ProducerRecord<String, String>("HelloKafkaTopic", msg));
        System.out.println("Sent:" + msg);
      }
    } catch (Exception e) {
      e.printStackTrace();

    } finally {
      producer.close();
    }

  }

}
