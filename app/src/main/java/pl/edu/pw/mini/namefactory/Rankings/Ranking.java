package pl.edu.pw.mini.namefactory.Rankings;

import java.util.List;

import pl.edu.pw.mini.namefactory.DatabasePackage.DatabaseHandler;
import pl.edu.pw.mini.namefactory.Names.Name;
import pl.edu.pw.mini.namefactory.RankingList;

/**
 * Created by Asus on 23.04.2017.
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

    public List<Name> getNames() {
        return dbh.getNameList(ID);
    }

    public String getNamesString() {
        return dbh.getNamesListAsString(ID).toString();
    }
}
