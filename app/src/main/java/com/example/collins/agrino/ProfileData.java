package com.example.collins.agrino;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import Fragments.Post;
import Fragments.Profile;
import Fragments.Updates;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileData extends AppCompatActivity {
    @BindView(R.id.profile_tool)Toolbar toolbar;
    @BindView(R.id.bottomNavProfile) BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_data);
        ButterKnife.bind(this);
        toolbar.setTitle("Updates");
        setSupportActionBar(toolbar);
        loadFragment(new Updates());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()){
                    case R.id.updates:
                        toolbar.setTitle("Updates");
                        fragment=new Updates();
                        loadFragment(fragment);
                        return  true;
                    case  R.id.post:
                        toolbar.setTitle("Post");
                        fragment=new Post();
                        loadFragment(fragment);
                        return  true;
                    case  R.id.profile:
                        toolbar.setTitle("Profile");
                        fragment=new Profile();
                        loadFragment(fragment);
                        return  true;
                }
                return false;
            }
        });
    }

    private  void  loadFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
