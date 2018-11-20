package Models;

public class User {
    public static  User user;
    String uId;
    String name;
    String email;

    public  User(   String uId,String name,String email){
        this.uId=uId;
        this.name=name;
        this.email=email;
        user=this;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getuId() {
        return uId;
    }
}
