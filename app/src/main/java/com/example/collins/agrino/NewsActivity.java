package com.example.collins.agrino;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsActivity extends AppCompatActivity implements  Services.OnFragmentInteractionListener,Resources.OnFragmentInteractionListener{
@BindView(R.id.recView) RecyclerView recyclerView;
@BindView(R.id.tabs) TabLayout tabLayout;
@BindView(R.id.viewPager) ViewPager viewPager;
    ArrayList<String> mnames=new ArrayList<String>();
    ArrayList<String> imageUrls=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        initImageBitMaps();
        initRecView();
        setTablayout();
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


    // Set Adapters

    private void initRecView(){
        Log.d("initRecView","init rec");
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new RecyclerViewAdapter(mnames,imageUrls,this));
    }

    private  void  setTablayout(){
        tabLayout.addTab(tabLayout.newTab().setText("Services"));
        tabLayout.addTab(tabLayout.newTab().setText("Resources"));
        PagerAdapter pagerAdapter=new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
