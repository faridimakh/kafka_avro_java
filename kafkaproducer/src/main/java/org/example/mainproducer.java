package org.example;

import org.example.toolkit.KafkaAvroproducer;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static org.example.toolkit.methodes.getPRODUCERCONFPATH;

public class mainproducer {
    public static void main(String[] args) throws IOException, JSONException {

        KafkaAvroproducer.start();


    }
}
