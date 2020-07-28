package com.example.gopal.quakereport1;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private final String USGS_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";
    private CustomAdapter mCustomAdapter;
    private List<Earthquake> mearthquakes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        CustomAsyncTask customAsyncTask = new CustomAsyncTask();
        customAsyncTask.execute(USGS_URL);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        mearthquakes = new ArrayList<>();

        // Create a new {@link ArrayAdapter} of earthquakes
        mCustomAdapter = new CustomAdapter(this, mearthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mCustomAdapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                //Earthquake currentEarthquake = mAdapter.getItem(position);
                Earthquake itemCliked = mearthquakes.get(position);
                Uri webpage = Uri.parse(itemCliked.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    private class  CustomAsyncTask extends AsyncTask<String,Void,List<Earthquake>> {


        @Override
        protected List<Earthquake> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            List<Earthquake> earthquakes = Utils.fetchEarthquakeData(urls[0]);
            return earthquakes;
        }

        @Override
        protected void onPostExecute(List<Earthquake> earthquakes) {

            // Clear the adapter of previous earthquake data
            mCustomAdapter.clear();
            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (earthquakes != null && !earthquakes.isEmpty()) {
                mCustomAdapter.addAll(earthquakes);
            }

        }
    }

}