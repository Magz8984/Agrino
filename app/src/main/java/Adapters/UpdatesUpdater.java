package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.collins.agrino.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import Models.Crop;
import Models.Formater;

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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Glide.with(context).asBitmap().load(crops.get(i).getUri()).into(viewHolder.uri);
        viewHolder.name.setText(crops.get(i).getName());
        viewHolder.caption.setText(crops.get(i).getCaption());
        if(crops.get(i).getName().equals(Formater.formater(FirebaseAuth.getInstance().getCurrentUser().getEmail()))){
            Log.d("SUCCESS","TRUE");
            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase.getInstance().getReference().child("crops").child(Long.toString(crops.get(i).getMills())).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Log.d("SUCCESS","TRUE");
                            }
                        }
                    });
                }
            });
        }
        else{
            viewHolder.btnDelete.setVisibility(View.GONE);
        }
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
    Button btnDelete;
    public  ViewHolder(View view){
        super(view);
        uri =(ImageView) view.findViewById(R.id.uri);
        name=(TextView) view.findViewById(R.id.name);
        caption=(TextView) view.findViewById(R.id.caption);
        btnDelete= (Button) view.findViewById(R.id.btnDelete);
    }
}