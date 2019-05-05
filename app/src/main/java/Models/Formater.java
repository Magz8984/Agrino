package Models;

public class Formater {
    public  static  String formater(String email){
        return  email.split("@")[0];
    }
}
