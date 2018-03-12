package weather.cs4985.westga.edu.thundercloud;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by onejo on 3/8/2018.
 */

public class JSONParser {
    JSONObject forecast;

    public JSONParser(String jsonfile) {
        try {
            forecast = new JSONObject(jsonfile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<String> forecastList() {

        JSONObject properties = null;
        ArrayList<String> list = new ArrayList<String>();
        try {
            properties = this.forecast.getJSONObject("properties");
        } catch (JSONException e) {
            return list;
        }

        JSONArray periods = null;
        try {
            periods = properties.getJSONArray("periods");
        } catch (JSONException e) {
            return list;
        }

        for (int i = 0; i < periods.length(); i++) {
            try {
                String name = periods.getJSONObject(i).getString("name");
                String shortForecast = periods.getJSONObject(i).getString("shortForecast");
                String temperature = periods.getJSONObject(i).getString("temperature");
                String forecast = name + "\n\t\t" + temperature + "\t\t" + shortForecast;
                list.add(forecast);
            } catch (JSONException e) {
                return list;
            }
        }
        return list;
    }
}
