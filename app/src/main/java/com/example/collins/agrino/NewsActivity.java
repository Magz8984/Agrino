package com.example.collins.agrino;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import services.AgriService;

public class NewsActivity extends AppCompatActivity {
@BindView(R.id.recView) RecyclerView recyclerView;
@BindView(R.id.lstItems) ListView  lstItems;
    ArrayList<String> mnames=new ArrayList<String>();
    ArrayList<String> imageUrls=new ArrayList<String>();
    ArrayList<String> headers=new ArrayList<String>();
    ArrayList<String> bodys=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        initImageBitMaps();
        initRecView();
        initServices();
        initListView();
    }

    private void initImageBitMaps(){
        imageUrls.add("https://www.mfarm.co.ke/media/W1siZiIsIjIwMTYvMDMvMTAvNHR3YjA5YzZxNV9jaGlsaWVzXzIuanBnIl0sWyJwIiwidGh1bWIiLCI4MjB4MzQwIyJdXQ/97f073f86e6cd0cf/chilies%202.jpg");
        mnames.add("Chillies");

        imageUrls.add("https://www.mfarm.co.ke/media/W1siZiIsIjIwMTUvMDEvMTUvNnJ0c3c0eHYyeV9vcm5nLmpwZyJdLFsicCIsInRodW1iIiwiODAweDM0MCMiXV0/76f4b648246d0a2b/orng.jpg");
        mnames.add("Oranges");

        imageUrls.add("https://www.mfarm.co.ke/media/W1siZiIsIjIwMTYvMDcvMjIvMW13eXJ1azA3OV9ZaWVsZGluZy5qcGciXSxbInAiLCJ0aHVtYiIsIjgwMHgzNDAjIl1d/9e3c311a1e83bf64/Yielding.jpg");

        mnames.add("Onions");

        imageUrls.add("https://www.mfarm.co.ke/media/W1siZiIsIjIwMTgvMTAvMzAvNXN5bnU4ZTlpcV9lZ2dzLmpwZyJdLFsicCIsInRodW1iIiwiMjEweDE0MCMiXV0/1160af2847dd1553/eggs.jpg");
        mnames.add("Eggs");

        imageUrls.add("https://www.mfarm.co.ke/media/W1siZiIsIjIwMTgvMTAvMzAvOTNlNzMyN3ZpaF9tYWl6ZV8xMDAuanBnIl0sWyJwIiwidGh1bWIiLCIyMTB4MTQwIyJdXQ/44326cbed9406eec/maize%20100.jpg");
        mnames.add("Dry Maize");

        imageUrls.add("https://www.mfarm.co.ke/media/W1siZiIsIjIwMTgvMTAvMTEvNjUxcDN1aTJ0ZF9JTUdfMjAxODEwMDJfV0EwMDAxLmpwZyJdLFsicCIsInRodW1iIiwiMjEweDE0MCMiXV0/d952e81bb421b870/IMG-20181002-WA0001.jpg");
        mnames.add("Chillies");

        imageUrls.add("https://www.mfarm.co.ke/media/W1siZiIsIjIwMTgvMTAvMDcvNWdycTBkcHJiOV9wZWFzLmpwZyJdLFsicCIsInRodW1iIiwiMjEweDE0MCMiXV0/f9b4da6a2ffd7a88/peas.jpg");
        mnames.add("Peas");

        imageUrls.add("https://www.mfarm.co.ke/media/W1siZiIsIjIwMTgvMTAvMTEvNGpqOXhybDE5X29yZ2FuaWNfc3VrdW1hd2lraV8xX2thbGVzLmpwZyJdLFsicCIsInRodW1iIiwiMjEweDE0MCMiXV0/e3cc7bdb18a938a9/organic_sukumawiki_1-kales.jpg");
        mnames.add("Kales");
    }

    private void initServices(){
        headers.add("Veterinary Officer");
        bodys.add("An experienced veterinary officer ready to present you top knock services");

        headers.add("National Farmers Information Service");
        bodys.add("NAFIS is a comprehensive information service, intended to serve farmers’ needs throughout the country including the rural areas where internet access is limited. " +
                "It enables farmers get critical extension information by either browsing through the internet or calling NAFIS 020-5100102 numbers");


        headers.add("Farm Drive");
        bodys.add("FarmDrive uses mobile phones, alternative data, and machine learning to close the critical data gap that prevents financial" +
                " institutions from lending to creditworthy smallholder farmers.");

        headers.add("Sokopepe");
        bodys.add("Sokopepe is a social enterprise supporting the agricultural sector in Kenya by offering market information and farm records management services");

    }

    // Set Adapters

    private void initRecView(){
        Log.d("initRecView","init rec");
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new RecyclerViewAdapter(mnames,imageUrls,this));
    }

    private void initListView(){
        Log.d("initListView","init list");
        lstItems.setAdapter(new ServicesAdapter(this,headers,bodys));
    }
}
