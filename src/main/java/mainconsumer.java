import com.org.far.principals.KafkaAvroConsumer;

import java.io.IOException;

public class mainconsumer {
    public static void main(String[] args) throws IOException {
        KafkaAvroConsumer.start("maonag");
    }
}
