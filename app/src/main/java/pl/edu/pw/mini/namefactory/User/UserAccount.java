package pl.edu.pw.mini.namefactory.User;

/**
 * Created by Asus on 10.05.2017.
 */

public class UserAccount {

    private int ID;
    private String name;
    private String userNickname;
    private String email;

    public void setName(String Name)
    {
        name = Name;
    }

    public String getName()
    {
        return name;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return  email;
    }

    public void setUserName(String username)
    {
        userNickname = username;
    }

    public String getUserName()
    {
        return  userNickname;
    }




}
