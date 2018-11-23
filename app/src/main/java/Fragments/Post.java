package Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.collins.agrino.CropActivity;
import com.example.collins.agrino.R;
import Adapters.PostAdapter;

public class Post extends Fragment {
    private GridView gridView;
    public Post() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_post, container, false);
        gridView=(GridView) view.findViewById(R.id.gridview);
        gridView.setAdapter(new PostAdapter(getContext()));


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:
                        Intent intent=new Intent(getActivity(), CropActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        break;
                }
            }
        });
        return view;
    }
}