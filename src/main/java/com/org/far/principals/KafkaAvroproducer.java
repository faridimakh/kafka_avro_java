package com.org.far.principals;

import com.example.Station;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.BasicConfigurator;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static com.org.far.toolkit.RecordsVilib.geOneRecordtStation;
import static com.org.far.toolkit.RecordsVilib.getJSONArray;
import static com.org.far.toolkit.constantes.*;

public class KafkaAvroproducer {


    public static void start() throws JSONException, IOException {
//        BasicConfigurator.configure();
        Properties producerProperties = new Properties();
        producerProperties.load(Files.newInputStream(Paths.get(getPRODUCERCONFPATH())));
        Producer<String, Station> producer = new KafkaProducer<String, Station>(producerProperties);
        while (true) {
            // copied from avro examples
            JSONArray getAllStations = getJSONArray(getVlibUrl());
            for (int i = 0; i < getAllStations.length(); i++) {
                Station station = geOneRecordtStation(getAllStations, i);
                ProducerRecord<String, Station> producerRecord = new ProducerRecord<>(

                        getTOPICSTATION(), station.getName().toLowerCase(), station);
                producer.send(producerRecord, (metadata, exception) -> {
                    if (exception == null) {
                        System.out.println(producerRecord.key()+ " -----> " + producerRecord.value().getContractName());
                    } else {
                        exception.printStackTrace();
                    }
                });
            };
        producer.flush();
        }

    }


}
