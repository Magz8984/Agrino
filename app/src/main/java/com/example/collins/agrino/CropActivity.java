package com.example.collins.agrino;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.Date;

import Models.Crop;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CropActivity extends AppCompatActivity  implements View.OnClickListener{
private ProgressDialog upload;
boolean selected=false;
private FirebaseStorage firebaseStorage;
private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("crops");
final  private  int REQUEST_CODE=1;
@BindView(R.id.cropToolbar) Toolbar toolbar;
@BindView(R.id.imgUpload) ImageView imgUpload;
@BindView(R.id.btnPostCrop) Button btnPostCrop;
@BindView(R.id.txtTalk) EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        ButterKnife.bind(this);
        btnPostCrop.setOnClickListener(this);
        firebaseStorage=FirebaseStorage.getInstance();
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imgUpload.setOnClickListener(this);
        initProgress();
    }

    @Override
    public void onClick(View v) {
        if(v.equals(imgUpload)){
            Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,REQUEST_CODE);
        }
        else  if(v.equals(btnPostCrop)){
            upload();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override

    public  void onActivityResult(int requestCode, int resultCode, Intent data){
        requestPermison();
            if(requestCode==REQUEST_CODE && data!=null){
                Uri selectedImage=data.getData();
                Glide.with(this).asBitmap().load(selectedImage).into(imgUpload);
                selected=true;
            }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public  void  requestPermison(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                Log.e("TRUE","TRUE");
            }
            else{
                Log.e("TRUE","FALSE");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        }
    }


    public  void upload(){
        final StorageReference storageReference;
        if(selected){
            upload.show();
            imgUpload.setDrawingCacheEnabled(true);
            imgUpload.buildDrawingCache();
            Bitmap bitmap=((BitmapDrawable)imgUpload.getDrawable()).getBitmap();
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
            byte[] bytes=baos.toByteArray();
            final String date=new Date().toLocaleString();
            storageReference=firebaseStorage.getReference(date);
            UploadTask uploadTask=storageReference.putBytes(bytes);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    upload.dismiss();
                    Log.d("Data",e.getMessage());
                }
            });
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    upload.dismiss();
                    storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            final Uri uri=task.getResult();
                             if(!editText.getText().toString().equals("")){
                                 getName().addValueEventListener(new ValueEventListener() {
                                     @Override
                                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                         long time=new Date().getTime();
                                         databaseReference.child(Long.toString(time)).setValue(new Crop(editText.getText().toString(),uri.toString(),dataSnapshot.child("name").getValue().toString(),
                                                 date,time)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                             @Override
                                             public void onComplete(@NonNull Task<Void> task) {
                                                 if(task.isSuccessful()){
                                                     Log.d("Done","Is Done");
                                                 }
                                             }
                                         });
                                     }

                                     @Override
                                     public void onCancelled(@NonNull DatabaseError databaseError) {

                                     }
                                 });
                             }
                        }
                    });
                }
            });
        }
        else{
           Toast.makeText(CropActivity.this,"Upload Photo",Toast.LENGTH_SHORT).show();
        }
    }
    private  void initProgress(){
        upload=new ProgressDialog(this);
        upload.setMessage("Uploading Image");
        upload.setCancelable(false);
    }


    private  DatabaseReference getName(){
        final String uid=FirebaseAuth.getInstance().getCurrentUser()
                .getUid();
        final String[] name = new String[1];
        return FirebaseDatabase.getInstance().getReference("users").child(uid);
    }
}
