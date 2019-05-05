package Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.collins.agrino.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Models.Formater;
import Models.User;

public class Profile extends Fragment {
    private FirebaseAuth firebaseAuth;
    DatabaseReference reference;


    public Profile() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_profile, container, false);
        firebaseAuth=FirebaseAuth.getInstance();
        TextView username=(TextView) view.findViewById(R.id.profileName);
        username.setText(Formater.formater(firebaseAuth.getCurrentUser().getEmail()));
        return view;
    }

}
