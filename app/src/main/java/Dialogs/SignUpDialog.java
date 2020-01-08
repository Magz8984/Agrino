package Dialogs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.collins.agrino.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Models.Formater;
import Models.User;

public class SignUpDialog extends DialogFragment implements  View.OnClickListener,OnCompleteListener {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private Button btnSignUp;
    private Button btnCancel;
    private EditText txtEmail;
    private EditText txtPassword;
    private  FirebaseAuth firebaseAuth;
    private  String email;
    private  String password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle){
        View view=inflater.inflate(R.layout.modal_sign_up,viewGroup,false);
        getDialog().setTitle("Sign Up");
        txtEmail=(EditText) view.findViewById(R.id.txtEmail2);
        txtPassword=(EditText) view.findViewById(R.id.txtPassword2);
        btnSignUp=(Button) view.findViewById(R.id.btnSignUp);
        btnCancel=(Button) view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        database=FirebaseDatabase.getInstance();
        return  view;
    }

    @Override
    public void onClick(View v) {
         if(v.equals(btnSignUp)){
             Log.d("Cred",txtEmail.getText().toString() + txtPassword.getText().toString());
             email=txtEmail.getText().toString();
             password=txtPassword.getText().toString();
             if(!email.isEmpty() && !password.isEmpty()){
                 signUpUser(email,password);
             }
             else{
                 Toast.makeText(getContext(),"Blank Fields",Toast.LENGTH_LONG).show();
             }
         }
         else if(v.equals(btnCancel)){
             try{
                 this.getDialog().cancel();
             }
             catch (Exception ex){
                 Log.d("Error",ex.getMessage());
             }
         }
    }

    public  void  setFirebaseAuth(FirebaseAuth firebaseAuth){
        this.firebaseAuth=firebaseAuth;
    }

    public  void signUpUser(String email,String password){
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(),this);
    }

    @Override
    public void onComplete(@NonNull Task task) {
        try {
            if(task.isSuccessful()){
                Toast.makeText(getContext(),"Successful",Toast.LENGTH_LONG).show();
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                            if(addUser(firebaseUser.getUid(), Formater.formater(email),email)){
                                Toast.makeText(getContext(),"Welcome " + Formater.formater(email) ,Toast.LENGTH_LONG ).show();
                            }
                        }
                        else{
                            Log.d("Not","Task Error");
                        }
                        SignUpDialog.this.getDialog().cancel();
                        Log.d("Done","Done");
                    }
                });
            }
            else{
                Toast.makeText(getContext(),"Authentication Failed",Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception ex){
            Log.d("Error",ex.getMessage());
        }
    }

    public boolean addUser(String userId,String name,String email ){
        databaseReference=database.getReference();
        return databaseReference.child("users").child(userId).setValue(new User(userId,name,email)).isSuccessful();
    }

}
