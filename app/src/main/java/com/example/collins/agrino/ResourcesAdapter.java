package com.example.collins.agrino;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import Models.Resource;

public class ResourcesAdapter extends RecyclerView.Adapter<ViewHolder>{
    private Context context;
    private ArrayList<Resource> resources;
    public ResourcesAdapter(Context context, ArrayList<Resource> resources) {
         this.context=context;
         this.resources=resources;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.activitty_resource_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.txtCrop.setText(resources.get(i).getName());
        viewHolder.txtDescription.setText(resources.get(i).getDescription());
        viewHolder.txtSource.setText(resources.get(i).getSource());
        Log.d("String",resources.get(i).getLink());
        if(!resources.get(i).getLink().equals("")) {
            Log.d("String",resources.get(i).getLink());
            final  int data=i;
            viewHolder.btnLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (resources.get(data).getLink().startsWith("h")){
                        Intent browserIntent=new Intent(Intent.ACTION_VIEW);
                        browserIntent.setData(Uri.parse(resources.get(data).getLink().trim()));
                        context.startActivity(browserIntent);
                    }
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return resources.size();
    }
}
class ViewHolder extends RecyclerView.ViewHolder{
    TextView txtCrop;
    TextView txtDescription;
    TextView txtSource;
    Button btnLink;
    ViewHolder(View view){
        super(view);
        txtCrop=(TextView) view.findViewById(R.id.txtCrop);
        txtDescription=(TextView) view.findViewById(R.id.txtDescription);
        txtSource=(TextView) view.findViewById(R.id.txtSource);
        btnLink=(Button) view.findViewById(R.id.btnLink);
    }
}
