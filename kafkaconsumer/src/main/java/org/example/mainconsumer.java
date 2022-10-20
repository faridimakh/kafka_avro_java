package org.example;


import org.example.toolkit.KafkaAvroConsumer;

import java.io.IOException;

import static org.example.toolkit.mthodes.getTOPICSTATION;

public class mainconsumer {
    public static void main(String[] args) throws IOException {
        KafkaAvroConsumer.start(getTOPICSTATION());
    }
}
