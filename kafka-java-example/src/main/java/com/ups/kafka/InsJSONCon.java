package com.ups.kafka;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import com.mysql.jdbc.StringUtils;


public class InsJSONCon {

  @SuppressWarnings("deprecation")
public static void main(final String[] args) {
	  System.out.println("Consumer");
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
    deleteDB();
    System.out.println("Consumer Hi");
   // String content="";
    while (true) {
    //	System.out.println("Hi3");
      ConsumerRecords<String, String> records = kafkaConsumer.poll(10);
      for (ConsumerRecord<String, String> record : records) {
    	//  System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());

/*    	
    	  MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
    	  @SuppressWarnings("resource")
		  MongoClient mongoClient = new MongoClient(connectionString);
     	  MongoDatabase database = mongoClient.getDatabase("zebraxyz");
     	  MongoCollection<Document> collection = database.getCollection("packages");
     	  
     	  System.out.println(record.value());
     	 
     	 Document dbObject = Document.parse(record.value()+1);
   //  	  DBObject dbObject = (DBObject)JSON.parse(record.value());
     	  
     	 collection.insertOne(dbObject);
     	System.out.println("Done Inserting in DB");
      	  
  */   	
    	//  String[] allrecords = record.value().split("\n");

       writeToDB(record.value());

      }

    }
  }
  
  public static void writeToDB(String record) {
      try {

    	 // MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
    	  MongoClientURI connectionString = new MongoClientURI("mongodb://ei-backend:FPztj4wn1ToNylWchNJIhplrvO7lrzHi3WKEvb87Itw4LOxBngtO2OQ8lp8fh4Ar14nzFauX6CCeoBSZgX5jnQ==@ei-backend.documents.azure.com:10255/?ssl=true&replicaSet=globaldb");
    	  @SuppressWarnings("resource")
		  MongoClient mongoClient = new MongoClient(connectionString);
    	  
    	  MongoDatabase database = mongoClient.getDatabase("zebraxyz");
    	  
    	  MongoCollection<Document> collection = database.getCollection("packages");
    	  
     	 // System.out.println(record);
     
          StringTokenizer recordToken = new StringTokenizer(record, "}}");
          while (recordToken.hasMoreTokens())
          {
              
              String newRecord = recordToken.nextToken().concat("}}");
              System.out.println(newRecord);
              Document dbObject = Document.parse(newRecord);
              collection.insertOne(dbObject);
              
          }
          
/*
     	 
     	 Document dbObject = Document.parse(record);
     	 System.out.println("dbObject.toString value : "+dbObject.toString());
     	 List <Document> list =  new ArrayList<>();
     	 list.add(dbObject);
   //  	  DBObject dbObject = (DBObject)JSON.parse(record.value());
     	 System.out.println("List: "+list);
    	  collection.insertMany(list);
    	  
 */   	  
    	
          System.out.println("Done DB inserts");

      } catch (Exception e) {
          e.printStackTrace();
      } 
  }
	  
  public static void deleteDB() {
      try {

    	  System.out.println("Flushing Started :)");
    	//  MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
    	  MongoClientURI connectionString = new MongoClientURI("mongodb://ei-backend:FPztj4wn1ToNylWchNJIhplrvO7lrzHi3WKEvb87Itw4LOxBngtO2OQ8lp8fh4Ar14nzFauX6CCeoBSZgX5jnQ==@ei-backend.documents.azure.com:10255/?ssl=true&replicaSet=globaldb");
    	  
    	  @SuppressWarnings("resource")
		  MongoClient mongoClient = new MongoClient(connectionString);
    	  
    	  //MongoDatabase database = mongoClient.getDatabase("zebraxyz");
    	  MongoDatabase database = mongoClient.getDatabase("zebraxyz");
    	  
    	  database.getCollection("packages").deleteMany(new Document());
    	  
	  
    	
          System.out.println("Cleaning Done :)");

      } catch (Exception e) {
          e.printStackTrace();
      } 
  }	  


}