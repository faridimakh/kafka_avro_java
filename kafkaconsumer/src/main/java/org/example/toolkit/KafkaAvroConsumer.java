package org.example.toolkit;

import com.example.Status;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

import static org.example.toolkit.mthodes.getCONSUMERCONFPATH;
import static org.example.toolkit.mthodes.getTOPICSTATION;


public class KafkaAvroConsumer {

    public static void start(String topicNAME) throws IOException {
        Properties consumerProperties = new Properties();
        consumerProperties.load(Files.newInputStream(Paths.get(getCONSUMERCONFPATH())));
        consumerProperties.load(Files.newInputStream(Paths.get(getCONSUMERCONFPATH())));

        try (KafkaConsumer<String, Status> kafkaConsumer = new KafkaConsumer<>(consumerProperties)) {
            String topic = getTOPICSTATION();
//            kafkaConsumer.subscribe(Collections.singleton(topic));
//            TopicPartition partition1 = new TopicPartition(topic, 0);
//            kafkaConsumer.assign(Arrays.asList(partition1));
            kafkaConsumer.subscribe(new ArrayList<>(Collections.singleton(topicNAME)));
            System.out.println("Waiting for data...");

            while (true) {
                System.out.println("Polling");
                ConsumerRecords<String, Status> records = kafkaConsumer.poll(1000);

                for (ConsumerRecord<String, Status> record : records) {

                    System.out.println(record.key() + "----------" + record.value());
                }

                kafkaConsumer.commitSync();
            }
        }
    }
}
