package weather.cs4985.westga.edu.thundercloud;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    final int TIMEOUT = 240;
    double lat = 33.575;
    double lon = -85.098;
    Handler handler;
    ThreadFetcher fetcher;
    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionRequester(Manifest.permission.ACCESS_FINE_LOCATION);
        permissionRequester(Manifest.permission.INTERNET);
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionRequester(Manifest.permission.ACCESS_FINE_LOCATION);
            permissionRequester(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            lat = location.getLatitude();
            lon = location.getLongitude();
        }
        String forecastURL = "https://api.weather.gov/points/" + lat + "," + lon + "/forecast";
        textview = (TextView) findViewById(R.id.textview);

        fetcher = new ThreadFetcher(forecastURL);
        fetcher.start();
        textview.setText("Retrieving Forcast");
        handler = new Handler();
        handler.post(checkFetcher);
    }

    Runnable checkFetcher = new Runnable() {
        int count = 0;

        public void run() {
            if (fetcher.isFinished()) {
                if (fetcher.successful()) {
                    JSONParser parser = new JSONParser(fetcher.getResult());
                    display(parser.forecastList());
                } else {
                    textview.setText(("Can't download forecast"));
                }

            } else {
                count++;
                if (count < TIMEOUT) {
                    handler.postDelayed(checkFetcher, 1000);
                } else {
                    textview.setText("No Network connection");
                }
            }
        }
    };

    private String prepareRequest() {
        return "https://api.weather.gov/points/" + lat + "," + lon + "/forecast";
    }

    private void permissionRequester(String resource) {

        int result = ContextCompat.checkSelfPermission(this, resource);
        if (result == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{resource}, 0);
        }

    }

    private void display(List<String> forecasts) {
        StringBuilder sb = new StringBuilder();
        for (String forecast : forecasts) {
            sb.append(forecast);
            sb.append("\n\n\n");
        }
        //  textview.setText("Hello world");
        textview.setText(sb.toString());
    }
}