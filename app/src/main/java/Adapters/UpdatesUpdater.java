package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.collins.agrino.R;

import java.util.ArrayList;

import Models.Crop;

public class UpdatesUpdater extends RecyclerView.Adapter<ViewHolder>{
    private Context context;
    private ArrayList<Crop> crops;
    public  UpdatesUpdater(Context context, ArrayList<Crop> crops){
        this.context=context;
        this.crops=crops;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.update_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(context).asBitmap().load(crops.get(i).getUri()).into(viewHolder.uri);
        viewHolder.name.setText(crops.get(i).getName());
        viewHolder.caption.setText(crops.get(i).getCaption());
    }

    @Override
    public int getItemCount() {
        return crops.size();
    }
}



class  ViewHolder extends  RecyclerView.ViewHolder{
    ImageView uri;
    TextView name;
    TextView caption;
    public  ViewHolder(View view){
        super(view);
        uri =(ImageView) view.findViewById(R.id.uri);
        name=(TextView) view.findViewById(R.id.name);
        caption=(TextView) view.findViewById(R.id.caption);
    }
}