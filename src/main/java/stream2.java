import com.example.Station;
import com.example.Status;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import static com.org.far.toolkit.constantes.getSTREAMCONFPATH;
import static com.org.far.toolkit.constantes.getTOPICSTATION;

public class stream2 {
    public static void main(String[] args) throws IOException, InterruptedException {

        BasicConfigurator.configure();
        final Properties props = new Properties();
        props.load(Files.newInputStream(Paths.get(getSTREAMCONFPATH())));
//-----------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------
        final Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url", "http://localhost:8081");

        final Serde<Station> valuestationserd = new SpecificAvroSerde<>();
        valuestationserd.configure(serdeConfig, false); // `false` for record values

        final Serde<Status> valuestatusserd = new SpecificAvroSerde<>();
        valuestatusserd.configure(serdeConfig, false); // `false` for record values
//-----------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, Station> strem_mepper = builder.stream(getTOPICSTATION(), Consumed.with(Serdes.String(), valuestationserd));
        KStream<String, Status> mymap = strem_mepper
                .map((s, station) -> new KeyValue<>(s, new Status(station.getContractName(), station.getStatus())));
        mymap.to("mapped_topic", Produced.with(Serdes.String(), valuestatusserd));
        Thread.sleep(100);
        //-----------------------------------------------------------------------------------------------------------------
        KStream<String, Status> strem_filter = builder.stream("mapped_topic", Consumed.with(Serdes.String(), valuestatusserd));
        strem_filter.filter((s, status) -> status.getContractName().equalsIgnoreCase("lyon"))
                .to("filtred_station", Produced.with(Serdes.String(), valuestatusserd));


//-----------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------

        Topology topovilib = builder.build();
        KafkaStreams strem = new KafkaStreams(topovilib, props);
        strem.start();
        Thread thread = new Thread(() ->
        {
            Logger.getLogger("application closed");
            strem.close();
        });
        Runtime.getRuntime().addShutdownHook(thread);

    }

}
