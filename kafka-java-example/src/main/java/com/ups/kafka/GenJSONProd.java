package com.ups.kafka;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class GenJSONProd {

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

//      for (int i = 0; i < 5; i++) {
    	  


      JSONParser parser = new JSONParser();
      
      FileReader file = new FileReader("/home/ei/rfid/Kafka/single.json");
      BufferedReader reader = new BufferedReader(file);
      
      
      // don't declare it here
      String json = "";
      String line = reader.readLine();

      while (line != null) {
    	  json += line;
          line = reader.readLine();
      }
     // System.out.println(json); 
      
      producer.send(new ProducerRecord<String, String>("HelloKafkaTopic", json));
      System.out.println("SENT"+json);
    //  System.out.println("Sent:" + data.toString());

      //Use JSONObject for simple JSON and JSONArray for array of JSON.
/*
            JSONObject data = (JSONObject) parser.parse(

            new FileReader("/home/ei/rfid/Kafka/single.json"));//path to the JSON file.

          

                 JSONArray data = (JSONArray) parser.parse(

                  new FileReader("/home/ei/rfid/Kafka/package.json"));//path to the JSON file.

    

      System.out.println(data);



      String json = data.toJSONString();

      System.out.println(json);      
      
        producer.send(new ProducerRecord<String, String>("HelloKafkaTopic", json));
        
        
        System.out.println("Sent:" + json);
   */        
//      }
    } catch (Exception e) {
      e.printStackTrace();

    } finally {
      producer.close();
    }

  }
  
  private static int showRandomInteger(int aStart, int aEnd, Random aRandom){
	    if (aStart > aEnd) {
	      throw new IllegalArgumentException("Start cannot exceed End.");
	    }
	    //get the range, casting to long to avoid overflow problems
	    long range = (long)aEnd - (long)aStart + 1;
	    // compute a fraction of the range, 0 <= frac < range
	    long fraction = (long)(range * aRandom.nextDouble());
	    int randomNumber =  (int)(fraction + aStart);    
	    return randomNumber;
	}

}
