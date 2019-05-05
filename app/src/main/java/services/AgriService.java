package services;


import android.util.Log;

import com.example.collins.agrino.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AgriService {
    public   static String ACCESS_TOKEN;
    public    static  void  fetchAccessToken(Callback callback){
        String hashedCredentials=Encoder.encodeData(Constants.AWHERE_KEY,Constants.AWHERE_SECRET);
        Log.d("HASHED CREDENTIALS",hashedCredentials);
        OkHttpClient okHttpClient= new OkHttpClient.Builder()
                .cache(null)
                .build();

        HttpUrl.Builder builder=HttpUrl.parse(Constants.CREDIENTAL_URL).newBuilder();
        String url=builder.build().toString();

        final FormBody requestBody = new FormBody.Builder()
                .add("grant_type","client_credentials")
                .build();

        Request request= new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Content-Type","application/x-www-form-urlencoded")
                .addHeader("Authorization","Basic "+hashedCredentials.trim())
                .build();

        Call call=okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public  static  String  retriveACESSTOKEN() throws  IOException{
        try {
            JSONObject jsonObject=new JSONObject(ACCESS_TOKEN);
            return  jsonObject.getString("access_token");

        }catch (JSONException ex){
            ex.printStackTrace();
        }
        return  null;
    }


    public static  Response getModels() throws IOException{
        OkHttpClient client =new OkHttpClient();

        HttpUrl.Builder urlBulder= HttpUrl.parse(Constants.CROP_URL).newBuilder();
        String url=urlBulder.build().toString();

        Request request=new Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer "+retriveACESSTOKEN())
                .build();
        return client.newCall(request).execute();
    }
}