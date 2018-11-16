package Dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.collins.agrino.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpDialog extends DialogFragment implements  View.OnClickListener,OnCompleteListener {
    private Button btnSignUp;
    private Button btnCancel;
    private EditText txtEmail;
    private EditText txtPassword;
    private  FirebaseAuth firebaseAuth;
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
        return  view;
    }

    @Override
    public void onClick(View v) {
         if(v.equals(btnSignUp)){
             Log.d("Cred",txtEmail.getText().toString() + txtPassword.getText().toString());
             String email=txtEmail.getText().toString();
             String password=txtPassword.getText().toString();
             if(!email.isEmpty() && !password.isEmpty()){
                 signUpUser(txtEmail.getText().toString(),txtPassword.getText().toString());
             }
             else{
                 Toast.makeText(getContext(),"Blank Fields",Toast.LENGTH_LONG).show();
             }
         }
         else if(v.equals(btnCancel)){
             this.getDialog().cancel();
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
        this.getDialog().cancel();
        if(task.isSuccessful()){
            Toast.makeText(getContext(),"Successful",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getContext(),"Authentication Failed",Toast.LENGTH_LONG).show();
        }
    }
}
