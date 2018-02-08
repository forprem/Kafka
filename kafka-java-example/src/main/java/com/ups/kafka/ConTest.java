package com.ups.kafka;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConTest {

  public static void main(String[] args) {
    Properties props = new Properties();
    props.put("bootstrap.servers", "127.0.0.1:9092");
    props.put("group.id", "group-1");
    props.put("enable.auto.commit", "true");
    props.put("auto.commit.interval.ms", "1000");
    props.put("auto.offset.reset", "earliest");
    props.put("session.timeout.ms", "30000");
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

    KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);
    kafkaConsumer.subscribe(Arrays.asList("HelloKafkaTopic"));
    while (true) {
      ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
      for (ConsumerRecord<String, String> record : records) {

    	    System.out.println("Hi");
    		String FILENAME = "D:\\Kafka\\Kafka.txt";
    		 
	        String content = "Partition: " + record.partition() + " Offset: " + record.offset()
	        + " Value: " + record.value() + " ThreadID: " + Thread.currentThread().getId(); 
    	    
	        System.out.println(content);
	        
    		 FileWriter fw = null;
    		 BufferedWriter bw = null;
    		 
    			try {


    				fw = new FileWriter(FILENAME);
    				bw = new BufferedWriter(fw);
    				bw.write(content);
    				System.out.println("Done File");

    				System.out.println("Done");	 
    		 
    	  } catch(final IOException e) {
    		  
    		  e.printStackTrace();
    		  
    	  } finally {

    			try {

    				if (bw != null)
    					bw.close();

    				if (fw != null)
    					fw.close();

    			} catch (final IOException ex) {

    				ex.printStackTrace();

    			}

    		}
   	  
    	  
    	  
    	  
    	  
    	  
        System.out.println("Partition: " + record.partition() + " Offset: " + record.offset()
            + " Value: " + record.value() + " ThreadID: " + Thread.currentThread().getId());
      }
    }

  }

}
