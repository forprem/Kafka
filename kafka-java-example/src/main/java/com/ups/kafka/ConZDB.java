package com.ups.kafka;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConZDB {

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
	  
	  
	    try
	    {
	      // loads com.mysql.jdbc.Driver into memory
	      Class.forName("com.mysql.jdbc.Driver");
	    } 
	    catch (ClassNotFoundException cnf) 
	    {
	      System.out.println("Driver could not be loaded: " + cnf);
	    }
	 
	    String connectionUrl = "jdbc:mysql://localhost:3306/zebra";
	    String dbUser = "prem";
	    String dbPwd = "adminUPS";
	    Connection conn;
	    ResultSet rs;
	    String queryString = "SELECT * FROM locations";
	 
	    try
	    {
	      conn = DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
	      Statement stmt = conn.createStatement();
	      java.sql.Timestamp ts = new java.sql.Timestamp(new java.util.Date().getTime());
	      
	      
	      //Sent:20,30,9#1Z000000009
	      //Sent:20,30,10#1Z0000000010
	      int hash = value.indexOf("#");
	      System.out.println("hash"+hash);
	      String coord = value.substring(0,hash);
	      System.out.println("coord"+coord);
	      String znum = value.substring(hash+1);
	      System.out.println("znum"+znum);
	      
	      
	 
	      // INSERT A RECORD
	      stmt.executeUpdate("INSERT INTO locations (coordinates,time_stamp,znum) VALUES ('"+coord+"','"+ts+"','"+znum+"')");
	 
	      // SELECT ALL RECORDS FROM EXPTABLE
	      rs = stmt.executeQuery(queryString);
	 
	      System.out.println("id \tValue");
	      System.out.println("============");
	      while(rs.next())
	      {
	        System.out.print(rs.getInt("id") + ".\t" + rs.getString("coordinates"));
	        System.out.println();
	      }
	      if (conn != null)
	      {
	        conn.close();
	        conn = null;
	      }
	    }
	    catch (SQLException sqle) 
	    {
	      System.out.println("SQL Exception thrown: " + sqle);
	    }

  }
}