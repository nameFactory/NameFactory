package pl.edu.pw.mini.namefactory;

/**
 * Created by Asus on 14.04.2017.
 */

public class Name {

    private String name;
    private int ID;

    public Name(int id, String name) {
        this.name = name;
        this.ID = id;
    }

    public int getID() {return ID;}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
