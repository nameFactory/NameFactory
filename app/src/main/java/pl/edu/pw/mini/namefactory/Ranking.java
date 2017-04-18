package pl.edu.pw.mini.namefactory;

import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by Asus on 14.04.2017.
 */

public class Ranking {
    private int ID;
    private String name;
    private DatabaseHandler dbh;

    //konstruktor do tworzenia obiektu z rekordu w tabeli (na potrzeby recyclingView)
    public Ranking(String name, int id) {
        this.dbh = RankingList.dbh;
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
        return dbh.getNamesListAsString(ID).toString();
    }
}
