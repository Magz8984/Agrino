package Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.collins.agrino.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import Animations.ItemMovement;
import Animations.Movements;
import Fragments.DeleteDialog;
import Models.Crop;
import Models.Formater;

public class UpdatesUpdater extends RecyclerView.Adapter<ViewHolder>  implements Movements{
    public StorageReference storageReference;
    public Context context;
    public ArrayList<Crop> crops;
    public FragmentManager fragmentManager;

    public UpdatesUpdater(Context context, ArrayList<Crop> crops, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager=fragmentManager;
        this.crops = crops;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.update_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Glide.with(context).asBitmap().load(crops.get(i).getUri()).into(viewHolder.uri);
        viewHolder.name.setText(crops.get(i).getName());
        viewHolder.caption.setText(crops.get(i).getCaption());
        viewHolder.lblTime.setText(crops.get(i).getDate());
        if (crops.get(i).getName().equals(Formater.formater(FirebaseAuth.getInstance().getCurrentUser().getEmail()))) {
            Log.d("SUCCESS", "TRUE");
            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItem(crops.get(i).getDate()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Done", Toast.LENGTH_LONG).show();
                            FirebaseDatabase.getInstance().getReference().child("crops").child(Long.toString(crops.get(i).getMills())).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("SUCCESS", "TRUE");
                                        notifyItemRemoved(i);
                                    }
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Unsuccessful", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            });
        } else {
            viewHolder.btnDelete.setVisibility(View.GONE);
        }
    }
    public StorageReference deleteItem(String date) {
        storageReference = FirebaseStorage.getInstance().getReference().child(date);
        return storageReference;
    }

    @Override
    public int getItemCount() {
        return crops.size();
    }


    @Override
    public void onItemDismiss(int position) {
        DeleteDialog deleteDialog=new DeleteDialog();
        deleteDialog.message="Are you sure you want to delete this?";
        deleteDialog.index=position;
        deleteDialog.updatesUpdater=this;
        deleteDialog.show(fragmentManager,"DELETE DIALOG");
    }
}

class  ViewHolder extends  RecyclerView.ViewHolder implements ItemMovement{
    ImageView uri;
    TextView name;
    TextView caption;
    Button btnDelete;
    TextView lblTime;
    View view;
     ViewHolder(View view){
        super(view);
        this.view=view;
        uri =(ImageView) view.findViewById(R.id.uri);
        name=(TextView) view.findViewById(R.id.name);
        caption=(TextView) view.findViewById(R.id.caption);
        btnDelete= (Button) view.findViewById(R.id.btnDelete);
        lblTime=(TextView) view.findViewById(R.id.lblTime);
    }

    @Override
    public void onItemSelected() {
        view.animate().alpha(0.7f)
                .scaleX(0.8f)
                .scaleY(0.8f)
                .setDuration(500);
    }

    @Override
    public void onItemClear() {
         view.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f);
    }
}


