package singlepageapp.mohanty.dinesh.com.earthquakeinfo;

/**
 * Created by debashish on 14-06-2017.
 */
public class Earthquake {

    private String place;
    Double magnitude;
    long date;
    String link;


    public Earthquake(Double magnitude ,String place , long date , String link)
    {
       this.place = place;
        this.date = date;
        this.magnitude = magnitude;
        this.link = link;

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

    public String getLink()
    {
        return link;
    }




}
