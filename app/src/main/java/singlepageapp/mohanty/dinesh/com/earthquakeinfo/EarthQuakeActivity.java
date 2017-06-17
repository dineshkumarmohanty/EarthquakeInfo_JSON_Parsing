package singlepageapp.mohanty.dinesh.com.earthquakeinfo;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class EarthQuakeActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Earthquake> Earthquakes;
    EarthquakeAdapter earthquakeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earth_quake);




        AsyncTask<String , Void , String> asyncTask = new EarthAsync();
        asyncTask.execute("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10");
        listView = (ListView)findViewById(R.id.list_main);

            if (listView != null) {

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if (Earthquakes != null) {

                            Earthquake earthquake = earthquakeAdapter.getItem(position);
                            Uri uri = Uri.parse(earthquake.getLink());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivity(intent);
                            }
                        }
                    }
                });
            }


    }




    private class EarthAsync extends AsyncTask<String , Void , String>
    {
        String jsonString = "";

        @Override
        protected String doInBackground(String... params) {
            URL url = createUrl(params[0]);
            try {
                jsonString = makeHttp(url);
            }
            catch (IOException e)
            {
            }

            return jsonString;
        }

        @Override
        protected void onPostExecute(String jsonString) {
            if(jsonString == null)
            {
                return;
            }
            else {
                Earthquakes = QueryUtils.extractEarthquakes(jsonString);
                earthquakeAdapter = new EarthquakeAdapter(EarthQuakeActivity.this, Earthquakes);
                listView.setAdapter(earthquakeAdapter);

            }
        }


    }


    private URL createUrl(String link) {
         URL url = null ;
        try {
             url = new URL(link);
        }
        catch (MalformedURLException e)
        {
            Log.e("LOG_TAG" , "Enter correct url" ,e);
            return null;
        }

            return url;
    }


    private String makeHttp(URL url) throws IOException {
        String jsonString = "";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            HttpsURLConnection httpsURLConnection =(HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setReadTimeout(10000 );
            httpsURLConnection.setConnectTimeout(15000);
            httpsURLConnection.connect();
            inputStream = httpsURLConnection.getInputStream();
             jsonString = getEartquake(inputStream);

        }
        catch (IOException e)
        {
            Log.e("Exception" , "IOexception" ,e);
            return null;
        }
        finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonString;
    }

    private String getEartquake(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();

    }


}
