package com.ups.kafka;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;


public class TestMongo {

    public static void main(String[] args) throws UnknownHostException{
        
    	try {
    	MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
	  
  	  @SuppressWarnings("resource")
		  MongoClient mongoClient = new MongoClient(connectionString);
  	  
  	  MongoDatabase database = mongoClient.getDatabase("zebraxyz");
  	  
  	  MongoCollection<Document> collection = database.getCollection("packages");
  	  
  	  String value = "1Z999999";
  	  String coordinatesx = "99";
  	  String coordinatesy = "39";
  	  String coordinatesz = "49";
  	  
  	  Document doc = new Document("znum", value)
                .append("coordinatesx", coordinatesx)
                .append("coordinatesy", coordinatesy)
                .append("coordinatesz", coordinatesz);

  	  collection.insertOne(doc);
  	
        System.out.println("Done");

    } catch (Exception e) {
        e.printStackTrace();
    } 
    }
}
