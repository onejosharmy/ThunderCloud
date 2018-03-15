package weather.cs4985.westga.edu.thundercloud;

import android.Manifest;
import android.app.ListActivity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends ListActivity {

    final int TIMEOUT = 240;
    double lat = 33.575;
    double lon = -85.098;
    Handler handler;
    ThreadFetcher fetcher;
    TextView textview;
    ListView listview;
    EntryAdapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
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
        listview = (ListView) findViewById(android.R.id.list);
        try {
            fetcher = new ThreadFetcher(forecastURL);
            fetcher.start();
            textview.setText(R.string.retrieving);
            handler = new Handler();
            handler.post(checkFetcher);
        } catch (Exception e) {
            textview.setText(R.string.failed);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreate(savedInstanceState);
            }
        });
    }

    Runnable checkFetcher = new Runnable() {
        int count = 0;

        public void run() {
            if (fetcher.isFinished()) {
                if (fetcher.successful()) {
                    textview.setText("");
                    JSONParser parser = new JSONParser(fetcher.getResult());
                    displayEntries(parser.forecastEntryList());
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

    private void permissionRequester(String resource) {

        int result = ContextCompat.checkSelfPermission(this, resource);
        if (result == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{resource}, 0);
        }

    }

    private void displayEntries(List<Entry> forecasts) {
        adapter = new EntryAdapter(MainActivity.this, R.layout.entry_view, forecasts);
        setListAdapter(adapter);
    }
}