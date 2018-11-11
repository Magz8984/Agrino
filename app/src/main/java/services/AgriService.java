package services;

import com.example.collins.agrino.Constants;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class AgriService {
    private  static String ACCESS_TOKEN;
    public  static  void  fetchAccessToken(Callback callback){
        OkHttpClient okHttpClient= new OkHttpClient();
        HttpUrl.Builder builder=HttpUrl.parse(Constants.CREDIENTAL_URL).newBuilder();
        String url=builder.build().toString();
        ResponseBody body=ResponseBody.create(MediaType.parse("grant_type"),"client_credentials");

        Request request= new Request.Builder()
                .url(url)
                .addHeader("Content-Type","")
                .addHeader("Authorization","Basic ")
                .build();

        Call call=okHttpClient.newCall(request);
        call.enqueue(callback);
    }
}