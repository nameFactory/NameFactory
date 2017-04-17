package pl.edu.pw.mini.namefactory;

import java.util.Collections;
import java.util.List;

/**
 * Created by Asus on 14.04.2017.
 */

public class Ranking {
    private int ID;
    private String name;
    private DatabaseHandler dbh;

    //konstruktor do tworzenia faktycznego obiektu i rekordu w tabeli
    public Ranking(DatabaseHandler dbh, String name) {
        this.dbh = dbh;
        this.name = name;
        this.ID = dbh.createRanking(name);
    }

    //konstruktor do tworzenia obiektu z rekordu w tabeli (na potrzeby recyclingView)
    public Ranking(DatabaseHandler dbh, String name, int id) {
        this.dbh = dbh;
        this.name = name;
        this.ID = id;
    }

    public int getID() {return ID;}

    public String getRankingName() {
        return name;
    }

    public void setRankingName(String name) {
        dbh.editRankingName(ID, name);
        this.name = name;
    }

    public String getNamesString() {
        return dbh.getNamesListAsString(ID).subList(0, 15).toString();
    }
}
