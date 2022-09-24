import com.org.far.principals.KafkaAvroproducer;
import org.json.JSONException;

import java.io.IOException;

public class mainproducer {
    public static void main(String[] args) throws IOException, JSONException {
        KafkaAvroproducer.start();
    }
}
