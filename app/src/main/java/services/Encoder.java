package services;
import android.util.Base64;

public class Encoder {
    public  static String encodeData(String key, String secret){
        key=String.format("%s:%s",key,secret);
        byte[] encodeData= Base64.encode(key.getBytes(),Base64.DEFAULT);
        return new String(encodeData);
    }
}
