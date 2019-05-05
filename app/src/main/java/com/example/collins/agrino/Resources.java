package com.example.collins.agrino;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import Models.Resource;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import services.AgriService;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Resources.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Resources#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Resources extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;

    public Resources() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Resources.
     */
    // TODO: Rename and change types and number of parameters
    public static Resources newInstance(String param1, String param2) {
        Resources fragment = new Resources();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_resources, container, false);
        linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        Log.d("Res", Integer.toString(Resource.getResouceSize()));
        recyclerView=(RecyclerView) view.findViewById(R.id.recResource);
        if(Resource.getResouceSize()>0){
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(new ResourcesAdapter(getContext(),Resource.getResources()));
        }
        else{
            if(isConnected()){
                getAccessToken();
            }
            else{
                Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
            }

        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public  void  getAccessToken(){
        AgriService.fetchAccessToken(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
//                Toast.makeText(getContext(),"Check Internet Connection",Toast.LENGTH_SHORT).show();
//                Looper.prepare();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
                    }
                });

                Log.d("NET ","No Internet");
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
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    recyclerView.setLayoutManager(linearLayoutManager);
                                    recyclerView.setAdapter(new ResourcesAdapter(getContext(),Resource.getResources()));
                                }
                            });
                    Log.d("Size",Integer.toString(Resource.getResouceSize()));
                }catch (JSONException ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    public  boolean isConnected(){
        try{
            ConnectivityManager connectivityManager=(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            return networkInfo.isConnected();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
