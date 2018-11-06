package com.example.collins.agrino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.login)Button  btnLogin;
    @BindView(R.id.txtEmail) TextView txtEmail;
    @BindView(R.id.txtPassword) TextView txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isEmailNotValid=txtEmail.getText().toString().equals("");
                boolean isPasswordNotValid=txtPassword.getText().toString().equals("") ;
//                Log.d("Email Not Valid",Boolean.toString(isEmailNotValid));
//                Log.d("Email Not  Password",Boolean.toString(isPasswordNotValid));
                if (!isEmailNotValid  && !isPasswordNotValid){
                    Intent i=new Intent(MainActivity.this,NewsActivity.class);
                    startActivity(i);
                }
                else Toast.makeText(MainActivity.this,"Blank Fields",Toast.LENGTH_LONG).show();
            }
        });
    }



    @Override
    public void onBackPressed(){
        moveTaskToBack(false);
    }
}
