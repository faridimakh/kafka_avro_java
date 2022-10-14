import com.example.Calcule;
import com.example.Station;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
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

public class statfull_stream {
    public static void main(String[] args) throws IOException, InterruptedException {

        BasicConfigurator.configure();
        final Properties props = new Properties();
        props.load(Files.newInputStream(Paths.get(getSTREAMCONFPATH())));
//-----------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------
        final Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url", "http://localhost:8081");

        final Serde<Station> SerdeStation = new SpecificAvroSerde<>();
        SerdeStation.configure(serdeConfig, false); // `false` for record values

//-----------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, Station> strem_mepper =
                builder.stream(getTOPICSTATION(), Consumed.with(Serdes.String(), SerdeStation))
                        .selectKey((s, station) -> station.getContractName());


        final Serde<Calcule> SerdeCalcule = new SpecificAvroSerde<>();
        SerdeCalcule.configure(serdeConfig, false);


        //-----------------------------------------------------------------------------------------------------------------
        KStream<String, Calcule> kstreamcalcul = strem_mepper.map((s, station) -> new KeyValue<>(s, new Calcule(station.getAvailableBikes())));

        kstreamcalcul.to("top_ic0", Produced.with(Serdes.String(), SerdeCalcule));


        //-----------------------------------------------------------------------------------------------------------------
        try {

            KStream<String, Calcule> abbouchiw = builder.stream("top_ic0", Consumed.with(Serdes.String(), SerdeCalcule));

            KGroupedStream<String, Calcule> grouped = abbouchiw.groupByKey();
            KTable<String, Calcule> finfar = grouped.aggregate(() -> new Calcule(0L), (s, calcule, calcule2) ->
                    new Calcule(calcule.getMasome() + calcule2.getMasome()), Materialized.with(Serdes.String(), SerdeCalcule));
            finfar.toStream().to("maonag", Produced.with(Serdes.String(), SerdeCalcule));
        } catch (Exception e) {
            System.out.println("----------------");
        }
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
