package com.example.collins.agrino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import Models.Resource;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import services.AgriService;

public class SpashScreen extends AppCompatActivity {

    @BindView(R.id.pgStart) ProgressBar pgStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash_screen);
        ButterKnife.bind(this);
        getAccessToken();
    }

    public  void  getAccessToken(){
        AgriService.fetchAccessToken(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(SpashScreen.this,"Check Internet Connection",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                AgriService.ACCESS_TOKEN=response.body().string();
                Log.d("ACCESS TOKEN",AgriService.retriveACESSTOKEN());
                Log.d("Crops",AgriService.getModels().body().string());
                try{
                    JSONObject jsonObject=new JSONObject(AgriService.getModels().body().string());
                    JSONArray resources=jsonObject.getJSONArray("models");

                    for (int i=0;i<resources.length();i++){
                        Resource resource=new Resource(resources.getJSONObject(i).getString("id"),
                                resources.getJSONObject(i).getString("name"),
                                resources.getJSONObject(i).getString("description"),
                                resources.getJSONObject(i).getJSONObject("source").getString("name"),
                                resources.getJSONObject(i).getJSONObject("source").getString("link"));
                    }
                    Log.d("Size",Integer.toString(Resource.getResouceSize()));
                }catch (JSONException ex){
                    ex.printStackTrace();
                }
                Intent i=new Intent(SpashScreen.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

}
