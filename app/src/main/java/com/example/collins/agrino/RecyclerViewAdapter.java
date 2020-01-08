package com.example.collins.agrino;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> mnames=new ArrayList<>();
    private ArrayList<String> imageurls=new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> mnames,ArrayList<String> imageurls,Context mContext){
        this.mnames=mnames;
        this.imageurls=imageurls;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d("On View Holder","Caller");
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Glide.with(mContext)
                .asBitmap()
                .load(imageurls.get(i))
                .into(viewHolder.circleImageView);
        viewHolder.name.setText(mnames.get(i));
        viewHolder.circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,mnames.get(i),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageurls.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView name;
        public ViewHolder(View view){
            super(view);
            circleImageView = view.findViewById(R.id.image);
            name=view.findViewById(R.id.txtName);
        }
    }
}
