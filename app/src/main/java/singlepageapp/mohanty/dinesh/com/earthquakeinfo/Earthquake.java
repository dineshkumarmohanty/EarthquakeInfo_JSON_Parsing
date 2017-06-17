package singlepageapp.mohanty.dinesh.com.earthquakeinfo;

/**
 * Created by debashish on 14-06-2017.
 */
public class Earthquake {

    private String place;
    Double magnitude;
    long date;


    public Earthquake(Double magnitude ,String place , long date)
    {
       this.place = place;
        this.date = date;
        this.magnitude = magnitude;

    }


    public long getDate()
    {
        return date;
    }
    public String getPlace()
    {
        return place;
    }


    public Double getMagnitude()
    {
        return magnitude;
    }




}
