package weather.cs4985.westga.edu.thundercloud;

import android.media.Image;

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

    public List<Entry> forecastEntryList(){
        JSONObject properties = null;
        ArrayList<Entry> list = new ArrayList<Entry>();
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
                String image = periods.getJSONObject(i).getString("icon");
                Entry theEntry = new Entry(name,shortForecast,temperature,image);
                String forecast = name + "\n\t\t" + temperature + "\t\t" + shortForecast + "\t\t" + image;
                list.add(theEntry);
            } catch (JSONException e) {
                return list;
            }
        }
        return list;
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
                String image = periods.getJSONObject(i).getString("icon");
                String forecast = name + "\n\t\t" + temperature + "\t\t" + shortForecast + "\t\t" + image;
                list.add(forecast);
            } catch (JSONException e) {
                return list;
            }
        }
        return list;
    }
}
