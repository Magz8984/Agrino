package Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.example.collins.agrino.ChatsActivity;
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
                        Intent chats=new Intent(getActivity(), ChatsActivity.class);
                        startActivity(chats);
                        break;
                }
            }
        });
        return view;
    }
}
