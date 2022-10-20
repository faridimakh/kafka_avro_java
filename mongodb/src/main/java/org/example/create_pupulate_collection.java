package org.example;

import com.mongodb.MongoClient;
import com.mongodb.MongoCommandException;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.web.util.Log4jConfigListener;
import sun.rmi.runtime.Log;

import java.util.Arrays;
import java.util.Collections;

import static org.example.toolkit.methodes.getJSONArray;
import static org.example.toolkit.methodes.getVlibUrl;

public class create_pupulate_collection {
    public static void main(String[] args) throws JSONException {

        MongoCredential credential = MongoCredential.createCredential("farid", "fardb", "ff".toCharArray());
        MongoClient mongo = new MongoClient(new ServerAddress("localhost", 27017), Collections.singletonList(credential));
        //Connecting to the database(we create previously the database with necessary credent-
        // ials)
        MongoDatabase database = mongo.getDatabase("fardb");
        try {
        database.createCollection("vilib1");

        } catch (MongoCommandException e){
            System.out.println(e);
        }
       //create vilib Collection in existing database fardb

        JSONArray getAllStations = getJSONArray(getVlibUrl());
        Document alldocs=new Document();
        for (int i = 0; i < getAllStations.length(); i++) {
            Document doc = Document.parse(getAllStations.get(i).toString());

            database.getCollection("vilib").insertOne(doc);
        }


    }
}
