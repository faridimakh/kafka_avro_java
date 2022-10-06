package packfartest;

import com.example.Station;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import static com.org.far.toolkit.constantes.getTOPICSTATION;

public class streamvilib {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        // When configuring the default serdes of StreamConfig
        final Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "iuiu");
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        streamsConfiguration.put("schema.registry.url", "http\\://127.0.0.1\\:8081");

        final Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url",
                "http://localhost:8081");
        final Serde<Station> valueSpecificAvroSerde = new SpecificAvroSerde<>();
        valueSpecificAvroSerde.configure(serdeConfig, false); // `false` for record values

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, Station> strem_transf_station = builder.stream(getTOPICSTATION(), Consumed.with(Serdes.String(), valueSpecificAvroSerde));
        strem_transf_station.filter((key, value) -> value.getContractName().equalsIgnoreCase("valence")).to("valence");


        Topology topovilib = builder.build();
        KafkaStreams strem = new KafkaStreams(topovilib, streamsConfiguration);
        strem.start();
        Thread thread = new Thread(() ->
        {
            Logger.getLogger("appliation closed");
            strem.close();
        });
        Runtime.getRuntime().addShutdownHook(thread);

    }

}
