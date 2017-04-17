package pl.edu.pw.mini.namefactory;

import java.util.Collections;
import java.util.List;

/**
 * Created by Asus on 14.04.2017.
 */

public class Ranking {
    private int ID;
    private String rankingName;
    private String namesString;

    public Ranking() {
        //to nalezy potem USUNAC
        namesString = "Kinga, Monika, Marcelina, Magda, Milena, Marysia, Maria, Marta";
    }

    public Ranking(String name) {
        this.rankingName = name;

        //uzupelnienie listy imion na podstawie bazy
        namesString = "Kinga, Monika, Marcelina, Magda, Milena, Marysia, Maria";
    }

    public int getID() {return ID;}

    public String getRankingName() {
        return rankingName;
    }

    public void setRankingName(String name) {
        this.rankingName = name;
    }

    public String getNamesString() {
        return namesString;
    }

    public void setNamesString(String namesToShow) {
        this.namesString = namesToShow;
    }

}
