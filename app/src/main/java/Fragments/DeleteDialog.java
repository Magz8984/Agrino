package Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.collins.agrino.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import Adapters.UpdatesUpdater;
public class  DeleteDialog extends DialogFragment implements  View.OnClickListener{
    public String message;
    public Button btnYes;
    public Button btnNo;
    public int index;
    public TextView lblMessage;
    public UpdatesUpdater updatesUpdater;
    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle){
        getDialog().setTitle("Verify Deletion");
        View view=inflater.inflate(R.layout.delete_update_layout,viewGroup,false);
        btnYes=(Button) view.findViewById(R.id.btnYes);
        btnNo=(Button) view.findViewById(R.id.btnNo);
        TextView lblMessage=(TextView) view.findViewById(R.id.lblMessage);
        lblMessage.setText(message);
        btnYes.setOnClickListener(this);
        btnNo.setOnClickListener(this);
        return  view;
    }

    @Override
    public void onClick(View v) {
        if(v.equals(btnNo)){
            getDialog().dismiss();
        }
        else  if(v.equals(btnYes)){
            deleteFromIndex();
        }
    }

    public  void deleteFromIndex(){
        updatesUpdater.deleteItem(updatesUpdater.crops.get(index).getDate()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(updatesUpdater.context, "Done", Toast.LENGTH_LONG).show();
                FirebaseDatabase.getInstance().getReference().child("crops").child(Long.toString(updatesUpdater.crops.get(index).getMills())).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("SUCCESS", "TRUE");
                            updatesUpdater.notifyItemRemoved(index);
                            getDialog().dismiss();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                getDialog().dismiss();
                Toast.makeText(updatesUpdater.context, "Unsuccessful", Toast.LENGTH_LONG).show();
            }
        });
    }

}