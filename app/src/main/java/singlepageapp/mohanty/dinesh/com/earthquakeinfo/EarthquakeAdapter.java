package singlepageapp.mohanty.dinesh.com.earthquakeinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by debashish on 14-06-2017.
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake>{
    String originalLocation ;
    String primaryLocation;
    String locationOffset;
    private static final String LOCATION_SEPARATOR = " of ";

   public EarthquakeAdapter(Context context , ArrayList<Earthquake> Earthquakes)
    {
        super(context  , 0 , Earthquakes);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView= convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Earthquake earthquake = getItem(position);

        DecimalFormat formatter = new DecimalFormat("0.00");
        String output = formatter.format(earthquake.getMagnitude());
        TextView textView = (TextView) listItemView.findViewById(R.id.magnitude_item);
        textView.setText(output);

        //change the colour of background


        //we split two string by a location separator


        originalLocation = earthquake.getPlace();
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset =  "near the";
            primaryLocation = originalLocation;
        }

        TextView textView4 = (TextView) listItemView.findViewById(R.id.place_item_first);
        textView4.setText(locationOffset);
        TextView textView1 = (TextView)listItemView.findViewById(R.id.place_item);
        textView1.setText(primaryLocation);


        Date dateObject = new Date(earthquake.getDate());
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");
        String dateToDisplay = dateFormatter.format(dateObject);

        TextView textView2 = (TextView)listItemView.findViewById(R.id.date_item);
        textView2.setText(dateToDisplay);



        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        String timeToDisplay = timeFormat.format(dateObject);

        TextView textView3 = (TextView)listItemView.findViewById(R.id.time_item);
        textView3.setText(timeToDisplay);



        return listItemView;
    }

}
