package packfartest;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.MongoClient;

import java.util.Arrays;

public class InsertingDocument {
    public static void main( String args[] ) {
        //Creating a MongoDB client
//        - MONGO_INITDB_ROOT_USERNAME=citizix
//                - MONGO_INITDB_ROOT_PASSWORD=S3cret
        MongoCredential credential = MongoCredential.createCredential("farid", "fardb", "ff".toCharArray());

        MongoClient mongo = new MongoClient(new ServerAddress("localhost", 27017), Arrays.asList(credential));
        //Connecting to the database
        MongoDatabase database = mongo.getDatabase("fardb");
//        //Creating a collection
        database.createCollection("students2");
//        //Preparing a document
        Document document = new Document();
        document.append("name", "Ram");
        document.append("age", 26);
        document.append("city", "Hyderabad");
//        //Inserting the document into the collection
        database.getCollection("students").insertOne(document);
        System.out.println("Document inserted successfully");
    }
}
