package singlepageapp.mohanty.dinesh.com.earthquakeinfo;


import android.content.Context;
import android.util.Log;

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

public class EarthquakeLoader extends android.support.v4.content.AsyncTaskLoader<ArrayList<Earthquake>>{

    String Link =  "";

    public EarthquakeLoader(Context context , String url)
    {
        super(context);
        Link = url;

    }

    String jsonString = "";

    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Earthquake> loadInBackground() {


        ArrayList<Earthquake> earthquakes = null;
        URL url = createUrl(Link);
        try {
            jsonString = makeHttp(url);
        }
        catch (IOException e)
        {
        }
        finally {
            earthquakes = QueryUtils.extractEarthquakes(jsonString);
        }


        return earthquakes;
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
