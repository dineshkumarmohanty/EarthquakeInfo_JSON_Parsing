package singlepageapp.mohanty.dinesh.com.earthquakeinfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class EarthQuakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Earthquake>>{
    ListView listView;
    ArrayList<Earthquake> Earthquakes;
    EarthquakeAdapter earthquakeAdapter;
    String Link = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earth_quake);

        listView = (ListView)findViewById(R.id.list_main);

        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(1 , null ,this);
        textView = (TextView)findViewById(R.id.empty_view);
        listView.setEmptyView(textView);





    }


    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int id, Bundle args) {
        return new EarthquakeLoader(EarthQuakeActivity.this , Link);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> data) {

        earthquakeAdapter = new EarthquakeAdapter(EarthQuakeActivity.this ,data);
        listView.setAdapter(earthquakeAdapter);

        textView.setText("No Item present");
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                        Earthquake earthquake = earthquakeAdapter.getItem(position);
                        Uri uri = Uri.parse(earthquake.getLink());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }

                }
            });







    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquake>> loader) {

    }
}
