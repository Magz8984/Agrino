package com.example.collins.agrino;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileData extends AppCompatActivity {
    @BindView(R.id.profile_tool)Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_data);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }
}
