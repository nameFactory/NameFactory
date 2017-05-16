package pl.edu.pw.mini.namefactory.Names;

/**
 * Created by Asus on 23.04.2017.
 */

public class Name {

    private String name;
    private int ID;
    private boolean isMale;

    public Name(int id, String name, boolean isMale) {
        this.name = name;
        this.ID = id;
        this.isMale = isMale;
    }

    public int getID() {return ID;}

    public boolean getIsGirl() {return !isMale;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
