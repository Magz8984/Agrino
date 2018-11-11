package com.example.collins.agrino;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class ResourcesActivity extends AppCompatActivity {
    @BindView(R.id.recResources) RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);
        ButterKnife.bind(this);
        linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        Log.d("Res", Integer.toString(Resource.getResouceSize()));

        if(Resource.getResouceSize()>0){
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(new ResourcesAdapter(ResourcesActivity.this,Resource.getResources()));
        }
        else{
            getAccessToken();
        }
    }
    public  void  getAccessToken(){
        AgriService.fetchAccessToken(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(ResourcesActivity.this,"Check Internet Connection",Toast.LENGTH_SHORT).show();
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
                    ResourcesActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(new ResourcesAdapter(ResourcesActivity.this,Resource.getResources()));
                        }
                    });
                    Log.d("Size",Integer.toString(Resource.getResouceSize()));
                }catch (JSONException ex){
                    ex.printStackTrace();
                }
            }
        });
    }
}
