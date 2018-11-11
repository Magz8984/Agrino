package com.example.collins.agrino;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SpashScreen extends AppCompatActivity {

    @BindView(R.id.pgStart) ProgressBar pgStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash_screen);
        ButterKnife.bind(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SpashScreen.this,MainActivity.class);
                startActivity(i);
            }
        },3000);
    }


}
