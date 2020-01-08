package com.example.collins.agrino;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ServicesAdapter extends BaseAdapter{
    private  Context context;
    private ArrayList<String> header;
    private ArrayList<String> body;

    public  ServicesAdapter(Context context, ArrayList<String> header,ArrayList<String> body){
        this.context=context;
        this.header=header;
        this.body=body;
    }
    @Override
    public int getCount() {
        return this.header.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView=null;
        if(convertView==null){
            rootView=inflater.inflate(R.layout.list_item,null);
            TextView header=(TextView) rootView.findViewById(R.id.itemheader);
            TextView body=(TextView) rootView.findViewById(R.id.itembody);
            header.setText(this.header.get(position));
            body.setText(this.body.get(position));
        }
        else{
            rootView=convertView;
        }
        return rootView;
    }
}
