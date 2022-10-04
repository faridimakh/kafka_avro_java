package packfartest;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import org.bson.Document;
import com.mongodb.MongoClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.Arrays;

import static com.org.far.toolkit.RecordsVilib.getJSONArray;
import static com.org.far.toolkit.constantes.getVlibUrl;

public class update_collection {
    public static void main( String args[] ) throws JSONException {

        MongoCredential credential = MongoCredential.createCredential("farid", "fardb", "ff".toCharArray());
        MongoClient mongo = new MongoClient(new ServerAddress("localhost", 27017), Arrays.asList(credential));
        //Connecting to the database
        MongoDatabase database = mongo.getDatabase("fardb");

        JSONArray getAllStations = getJSONArray(getVlibUrl());
        for (int i = 0; i < getAllStations.length(); i++) {
            Document doc = Document.parse(getAllStations.get(i).toString());

            database.getCollection("vilib")
                    .findOneAndReplace(Filters.eq("name", getAllStations.getJSONObject(i)
                            .get("name")), doc,new FindOneAndReplaceOptions().upsert(true)
                            .returnDocument(ReturnDocument.BEFORE));
        }



    }
}
