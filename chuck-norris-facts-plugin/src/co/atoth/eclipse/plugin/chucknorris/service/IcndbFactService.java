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

import org.eclipse.json.provisonnal.com.eclipsesource.json.JsonArray;
import org.eclipse.json.provisonnal.com.eclipsesource.json.JsonObject;

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

        JsonObject jsonObject = JsonObject.readFrom(response);
        
        //Parse response JSON
        if (jsonObject.get("type") != null && 
        		jsonObject.get("type").isString() && 
        		jsonObject.get("type").asString().equals("success") && 
        		jsonObject.get("value") != null) {

            if (settings.getFactLoadCount() > 1) {
                JsonArray factArray = jsonObject.get("value").asArray();
                for (int i = 0; i < factArray.size(); i++) {
                	JsonObject fact = factArray.get(i).asObject();
                    result.add(new Fact(fact.get("id").asLong() + "", fact.get("joke").asString()));
                }
            } else {
            	JsonObject fact = jsonObject.get("value").asObject();
                result.add(new Fact(fact.get("id").asLong() + "", fact.get("joke").asString()));
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