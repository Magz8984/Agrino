package Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collins.agrino.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Adapters.UpdatesUpdater;
import Animations.SimpleAnimation;
import Models.Crop;

public class Updates extends Fragment {
    RecyclerView updates;
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("crops");
    public Updates() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getCrops();
        View view=inflater.inflate(R.layout.fragment_updates, container, false);
        updates=view.findViewById(R.id.recUpdates);
        updates.setLayoutManager(new LinearLayoutManager(getContext()));


        return  view;
    }


    public  void getCrops(){
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Crop.crops.clear();
                    if(dataSnapshot.getChildrenCount()>=1){
                        for (DataSnapshot crop:dataSnapshot.getChildren()){
                            Crop plant=new Crop(crop.child("caption").getValue().toString()
                                    ,crop.child("uri").getValue().toString(),
                                    crop.child("name").getValue().toString(),
                                    crop.child("date").getValue().toString(),
                                    Long.parseLong(crop.child("mills").getValue().toString()));
                            Crop.crops.add(plant);
                        }

                        UpdatesUpdater updatesUpdater=new UpdatesUpdater(getContext(),Crop.crops,getFragmentManager());
                        updates.setAdapter(updatesUpdater);
                        SimpleAnimation simpleAnimation=new SimpleAnimation(updatesUpdater);
                        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleAnimation);
                        itemTouchHelper.attachToRecyclerView(updates);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }
}
