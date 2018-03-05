package com.ups.kafka;


import java.io.FileReader;
import java.io.IOException;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

 

 

public class GenJSON {

      

       public static void main(String[] args) {

 

 

           try {

               JSONParser parser = new JSONParser();

               //Use JSONObject for simple JSON and JSONArray for array of JSON.

/*             JSONObject data = (JSONObject) parser.parse(

                     new FileReader("D:\\Project\\AR\\singlw.json"));//path to the JSON file.

*/            

               JSONArray data = (JSONArray) parser.parse(

                           new FileReader("/home/ei/rfid/Kafka/package.json"));//path to the JSON file.

              

               System.out.println(data);

 

               String json = data.toJSONString();

               System.out.println(json);

           } catch (IOException | ParseException e) {

               e.printStackTrace();

           }

 

       }

 

}