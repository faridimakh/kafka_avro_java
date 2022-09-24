package com.org.far;

import com.org.far.principals.KafkaAvroConsumer;
import com.org.far.principals.KafkaAvroproducer;
import org.json.JSONException;

import java.io.IOException;

public class mainclass {
    public static void main(String[] args) throws IOException, JSONException {
        KafkaAvroproducer.start();
        KafkaAvroConsumer.start();
    }
}
