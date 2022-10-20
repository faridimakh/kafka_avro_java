package org.example.toolkit;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.web.client.RestTemplate;

public class methodes {
    public static String getVlibUrl() {
        return "https://api.jcdecaux.com/vls/v1/stations?apiKey=2a5d13ea313bf8dc325f8783f888de4eb96a8c14";
    }
    public static JSONArray getJSONArray(String someurl) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(someurl, String.class);
        return new JSONArray(response);
    }
}
