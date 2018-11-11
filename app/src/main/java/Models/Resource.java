package Models;
import android.util.Log;

import  java.util.ArrayList;
public class Resource {
    private   static  ArrayList<Resource> resources=new ArrayList<>();
    private  String id;
    private  String name;
    private  String description;
    private  String source;
    private  String link;

    public  Resource(String id,String name,String description,String source,String link){
        this.id=id;
        this.name=name;
        this.description=description;
        this.source=source;
        this.link=link;
        Resource.resources.add(this);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getSource() {
        return source;
    }

    public String getLink() {
        if(link.equals("null")){
            Log.d("DATA","DONE");
            return "";
        }
        else{
            return  link;
        }
    }
    public  static  ArrayList<Resource> getResources(){
        return  resources;
    }

    @Override
    public  String toString(){
        return name;
    }
    public  static  int getResouceSize(){
        return resources.size();
    }
}
