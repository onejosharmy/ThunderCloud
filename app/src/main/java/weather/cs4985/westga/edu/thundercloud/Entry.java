package weather.cs4985.westga.edu.thundercloud;


/**
 * Created by onejo on 1/24/2018.
 *
 */

public class Entry {

    private String name;
    private String shortForecast;
    private String temperature;
    private String imageResource;


    public Entry(String name, String shortForescast, String temperature, String imageResource) {
        this.name = name;
        this.shortForecast = shortForescast;
        this.temperature = temperature;
        this.imageResource = imageResource;
    }

    public String getName(){ return this.name; }

    public String getShortForecast(){ return this.shortForecast; }

    public String getTemperature() { return this.temperature; }

    public String getImageResource() { return this.imageResource; }
}
