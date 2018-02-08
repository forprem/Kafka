package com.ups.kafka;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;


import com.mongodb.client.MongoCursor;

import java.util.ArrayList;
import java.util.List;

public class MongoT {

    public static void main(String[] args) throws UnknownHostException{
        
        // Create seed data
        
        List<Document> seedData = new ArrayList<Document>();

        seedData.add(new Document("znum", "1Z10234")
        	.append("coordinatesx", "24")	
            .append("coordinatesy", "34")
            .append("coordinatesz", "44")
        );

        seedData.add(new Document("znum", "1Z10235")
            	.append("coordinatesx", "14")	
                .append("coordinatesy", "24")
                .append("coordinatesz", "34")
        );

        seedData.add(new Document("znum", "1Z10237")
            	.append("coordinatesx", "20")	
                .append("coordinatesy", "30")
                .append("coordinatesz", "40")
        );

        // Standard URI format: mongodb://[dbuser:dbpassword@]host:port/dbname
       
        MongoClientURI uri  = new MongoClientURI("mongodb://localhost:27017/zebraxyz"); 
        MongoClient client = new MongoClient(uri);
        MongoDatabase db = client.getDatabase(uri.getDatabase());
        
        /*
         * First we'll add a few songs. Nothing is required to create the
         * songs collection; it is created automatically when we insert.
         */
        
        MongoCollection<Document> songs = db.getCollection("packages");

        // Note that the insert method can take either an array or a document.
        
        songs.insertMany(seedData);
       
        /*
         * Then we need to give Boyz II Men credit for their contribution to
         * the hit "One Sweet Day".
         */

        Document updateQuery = new Document("znum", "1Z10235");
        songs.updateOne(updateQuery, new Document("$set", new Document("artist", "Mariah Carey ft. Boyz II Men")));
        
        /*
         * Finally we run a query which returns all the hits that spent 10 
         * or more weeks at number 1.
         */
      
        Document findQuery = new Document("coordinatesy", new Document("$gte",10));
        Document orderBy = new Document("coordinatesx", 1);

        MongoCursor<Document> cursor = songs.find(findQuery).sort(orderBy).iterator();

        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                System.out.println(
                    "In the " + doc.get("coordinatesx") + ", " + doc.get("coordinatesy") + 
                    " by " + doc.get("znum") + " topped the charts for " + 
                    doc.get("coordinatesy") + " straight weeks."
                );
            }
        } finally {
            cursor.close();
        }

        // Since this is an example, we'll clean up after ourselves.

    //    songs.drop();
        
        // Only close the connection when your app is terminating

        client.close();
    }
}