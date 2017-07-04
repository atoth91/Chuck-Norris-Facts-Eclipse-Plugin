package co.atoth.eclipse.plugin.chucknorris.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import co.atoth.eclipse.plugin.chucknorris.preferences.PluginSettings;

public class IcndbFactService implements FactService {

    private static final String API_HOST = "api.icndb.com";
    private static final String API_SCHEME = "http";
    private static final String API_BASE_PATH = "/jokes/random";
    private static final String API_ENCODING = "UTF-8";

    @Override
    public List<Fact> getRandomFacts(PluginSettings settings) {

        List<Fact> result = new ArrayList<>();
        
        String url = API_SCHEME + "://" + API_HOST + "/" + API_BASE_PATH;
        
        if (settings.getFactLoadCount() != 1) {
        	url += ("/" + settings.getFactLoadCount());
        }
        
        if (!PluginSettings.DEFAULT_FIRST_NAME.equals(settings.getFactFirstName())
                && !PluginSettings.DEFAULT_LAST_NAME.equals(settings.getFactLastName())) {

        	url += ("?firstName=" + settings.getFactFirstName());
        	url += ("&lastName=" + settings.getFactLastName());
        }

        URLConnection connection;
        String response;
        try {
            connection = new URL(url).openConnection();
            response = responseToString(connection.getInputStream(), API_ENCODING);
        } catch (IOException e) {
            return result;
        }

      //Parse response JSON
        JSONObject jsonObject = new JSONObject(response);
        if (jsonObject.has("type") && jsonObject.getString("type").equals("success") && jsonObject.has("value")) {

            if (settings.getFactLoadCount() > 1) {
                JSONArray factArray = jsonObject.getJSONArray("value");
                for (int i = 0; i < factArray.length(); i++) {
                    JSONObject fact = factArray.getJSONObject(i);
                    result.add(new Fact(fact.getLong("id") + "", fact.getString("joke")));
                }
            } else {
                JSONObject fact = jsonObject.getJSONObject("value");
                result.add(new Fact(fact.getLong("id") + "", fact.getString("joke")));
            }
        }

        return result;
    }
    
    private static String responseToString(InputStream inputStream, String charsetName){
        try {
            return new BufferedReader(new InputStreamReader(inputStream, charsetName)).lines().parallel().collect(Collectors.joining("\n"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

}