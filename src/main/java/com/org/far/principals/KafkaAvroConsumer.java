package com.org.far.principals;

import com.example.Station;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;

import static com.org.far.toolkit.constantes.getCONSUMERCONFPATH;
import static com.org.far.toolkit.constantes.getTOPICSTATION;

public class KafkaAvroConsumer {

    public static void start() throws IOException {
        Properties consumerProperties = new Properties();
        consumerProperties.load(Files.newInputStream(Paths.get(getCONSUMERCONFPATH())));

        try (KafkaConsumer<String, Station> kafkaConsumer = new KafkaConsumer<>(consumerProperties)) {
            String topic = getTOPICSTATION();
//            kafkaConsumer.subscribe(Collections.singleton(topic));
            TopicPartition partition1 = new TopicPartition(topic, 0);
            kafkaConsumer.assign(Arrays.asList(partition1));
            System.out.println("Waiting for data...");

            while (true) {
                System.out.println("Polling");
                ConsumerRecords<String, Station> records = kafkaConsumer.poll(1000);

                for (ConsumerRecord<String, Station> record : records) {

                    System.out.println(record.key()+ "----------" +record.value().getContractName());
                }

                kafkaConsumer.commitSync();
            }
        }
    }
}
