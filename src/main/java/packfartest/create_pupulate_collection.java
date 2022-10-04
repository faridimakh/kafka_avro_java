package packfartest;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.Arrays;

import static com.org.far.toolkit.RecordsVilib.getJSONArray;
import static com.org.far.toolkit.constantes.getVlibUrl;

public class create_pupulate_collection {
    public static void main(String[] args) throws JSONException {

        MongoCredential credential = MongoCredential.createCredential("farid", "fardb", "ff".toCharArray());
        MongoClient mongo = new MongoClient(new ServerAddress("localhost", 27017), Arrays.asList(credential));
        //Connecting to the database
        MongoDatabase database = mongo.getDatabase("fardb");
       //create vilib Collection in existing database fardb
        database.createCollection("vilib");

        JSONArray getAllStations = getJSONArray(getVlibUrl());
        Document alldocs=new Document();
        for (int i = 0; i < getAllStations.length(); i++) {
            Document doc = Document.parse(getAllStations.get(i).toString());

            database.getCollection("vilib").insertOne(doc);
        }


    }
}
