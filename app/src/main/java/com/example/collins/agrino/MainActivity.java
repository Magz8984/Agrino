package com.example.collins.agrino;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import Dialogs.SignUpDialog;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth=null;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ProgressDialog progressDialog;
    @BindView(R.id.login)Button  btnLogin;
    @BindView(R.id.signUp)Button btnSignUp;
    @BindView(R.id.txtEmail) TextView txtEmail;
    @BindView(R.id.txtPassword) TextView txtPassword;
    @BindView(R.id.lblForgotPassword) TextView lblForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        firebaseAuth=FirebaseAuth.getInstance();
        setDialog();
        addAuth();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isEmailNotValid=txtEmail.getText().toString().equals("");
                boolean isPasswordNotValid=txtPassword.getText().toString().equals("") ;
                if (!isEmailNotValid  && !isPasswordNotValid){
                    if(isConnected()){
                        login(txtEmail.getText().toString(),txtPassword.getText().toString());
                    }
                    else{
                        Toast.makeText(MainActivity.this,"No Internet",Toast.LENGTH_LONG).show();
                    }

                }
                else Toast.makeText(MainActivity.this,"Blank Fields",Toast.LENGTH_LONG).show();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpDialog signUpDialog=new SignUpDialog();
                signUpDialog.setFirebaseAuth(firebaseAuth);
                signUpDialog.show(MainActivity.this.getSupportFragmentManager(),"Sign Up Fragment");
            }
        });


        lblForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isEmailNotValid=txtEmail.getText().toString().equals("");
                if (!isEmailNotValid) {
                    if(isConnected()){
                        firebaseAuth.sendPasswordResetEmail(txtEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(MainActivity.this,"Email Sent",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,"Email Field Empty",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected  void onStart(){
        super.onStart();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user!=null){
            firebaseAuth.addAuthStateListener(authStateListener);
        }
    }

    @Override
    protected  void  onStop(){
        super.onStop();
        if(authStateListener!=null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(false);
    }
    public void addAuth(){
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent=new Intent(MainActivity.this,NewsActivity.class);
                    startActivity(intent);
                }
            }
        };
    }
    public  void login(String email,String password){
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Intent intent=new Intent(MainActivity.this,NewsActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this,"Authentication Failed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void setDialog(){
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Authentication In Progress");
        progressDialog.setMessage("Loading....");
        progressDialog.setCancelable(true);
    }
    public  boolean isConnected(){
        try{
            ConnectivityManager connectivityManager=(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            assert networkInfo != null;
            return networkInfo.isConnected();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
}
