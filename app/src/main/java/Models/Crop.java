package Models;

import android.net.Uri;

import java.util.ArrayList;

public class Crop {
    public  static ArrayList<Crop> crops=new ArrayList<>();
    private  String name;
    private  String caption;
    private  String uri;
    private  long mills;
    public  Crop(String caption,String uri,String name,long mills){
        this.uri=uri;
        this.caption=caption;
        this.name=name;
        this.mills=mills;
    }
    public String getCaption() {
        return caption;
    }
    public String getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }

    public long getMills() {
        return mills;
    }
}
