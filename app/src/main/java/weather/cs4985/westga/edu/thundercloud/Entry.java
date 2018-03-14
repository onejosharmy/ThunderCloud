package weather.cs4985.westga.edu.thundercloud;


/**
 * Created by onejo on 1/24/2018.
 *
 */

public class Entry implements Comparable<Entry> {

    private String longForecast;
    private String shortForecast;
    private int forecastOrder;


    public Entry(String longForecast, String shortForescast, int forecastOrder) {
        this.longForecast = longForecast;
        this.shortForecast = shortForescast;
        this.forecastOrder = forecastOrder;
    }

    public String getLongForecast(){ return this.longForecast; }

    public String getShortForecast(){ return this.shortForecast; }

    public int getforecastOrder() { return this.forecastOrder; }

    @Override
    public int compareTo(Entry that) {
        return this.forecastOrder - that.forecastOrder;
    }
}
