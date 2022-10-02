package com.org.far.principals;

import com.example.Station;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static com.org.far.toolkit.RecordsVilib.*;
import static com.org.far.toolkit.constantes.getPRODUCERCONFPATH;
import static com.org.far.toolkit.constantes.getTOPICSTATION;

public class KafkaAvroproducer {


    public static void start() throws JSONException, IOException {
        Properties producerProperties = new Properties();
        producerProperties.load(Files.newInputStream(Paths.get(getPRODUCERCONFPATH())));
        Producer<String, Station> producer = new KafkaProducer<String, Station>(producerProperties);
        while (true) {
            String topic = getTOPICSTATION();
            // copied from avro examples
            JSONArray getAllStations = getJSONArray(urlf);
            for (int i = 0; i < getAllStations.length(); i++) {
                Station station = geOneRecordtStation(getAllStations, i);
                ProducerRecord<String, Station> producerRecord = new ProducerRecord<>(

                        topic,station.getName(), station);
                producer.send(producerRecord, (metadata, exception) -> {
                    if (exception == null) {
                        System.out.println(metadata.offset()+" ----->"+producerRecord.value().getContractName());
                    } else {
                        exception.printStackTrace();
                    }
                });
            };
        producer.flush();
        }

    }


}
