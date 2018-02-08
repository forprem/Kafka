package com.ups.kafka;



import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.bson.Document;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class ConMon {

  public static void main(final String[] args) {
	  System.out.println("Hi");
    Properties props = new Properties();
    props.put("bootstrap.servers", "127.0.0.1:9092");
    //props.put("group.id", "group-1");
    props.put("group.id", "test-consumer-group");
    props.put("enable.auto.commit", "true");
    props.put("auto.commit.interval.ms", "1000");
    props.put("auto.offset.reset", "earliest");
    props.put("session.timeout.ms", "30000");
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

    @SuppressWarnings("resource")
	KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);
    kafkaConsumer.subscribe(Arrays.asList("HelloKafkaTopic"));
    System.out.println("Hi2");
   // String content="";
    while (true) {
    //	System.out.println("Hi3");
      ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
      for (ConsumerRecord<String, String> record : records) {
    	
    	System.out.println("Before Content");  
/*        String content = "Partition: " + record.partition() + " Offset: " + record.offset()
        + " Value: " + record.value() + " ThreadID: " + Thread.currentThread().getId();*/
        
        System.out.println("Before File call");
       writeToDB(record.partition(), record.offset(), record.value(), Thread.currentThread().getId() );
        System.out.println("After File call");
/*    	  System.out.println("Partition: " + record.partition() + " Offset: " + record.offset()
            + " Value: " + record.value() + " ThreadID: " + Thread.currentThread().getId());*/
      }
      //writeToDB(content);
    }
  }
  
  public static void writeToDB(int partition, long offset, String value, long TID) {
      try {

    	  MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
    	  
    	  @SuppressWarnings("resource")
		  MongoClient mongoClient = new MongoClient(connectionString);
    	  
    	  MongoDatabase database = mongoClient.getDatabase("zebraxyz");
    	  
    	  MongoCollection<Document> collection = database.getCollection("packages");
    	  
	      //Sent:20,30,9#1Z000000009
	      //Sent:20,30,10#1Z0000000010
	      int hash = value.indexOf("#");
	     // System.out.println("hash: "+hash);
	      
	      String coord = value.substring(0,hash);
	      //System.out.println("coord: "+coord);
	      
	      String znum = value.substring(hash+1);
	     // System.out.println("znum: "+znum);
	      
	      java.sql.Timestamp ts = new java.sql.Timestamp(new java.util.Date().getTime());

	      
    	  Document doc = new Document("znum", znum)
                  .append("coordinatesx", coord.substring(0, 2))
                  .append("coordinatesy", coord.substring(3, 5))
                  .append("coordinatesz", coord.substring(6))
                  .append("Timestamp", ts );

    	  collection.insertOne(doc);
    	
          System.out.println("Done");

      } catch (Exception e) {
          e.printStackTrace();
      } 
  }
	  
	  
	    


}