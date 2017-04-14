package pl.edu.pw.mini.namefactory;

import java.util.Collections;
import java.util.List;

/**
 * Created by Asus on 14.04.2017.
 */

public class Ranking {
    private String rankingName;
    private String namesString;
    private List<Name> names;
    private List<String> tags;

    public Ranking() {
        //to nalezy potem USUNAC
        namesString = "Kinga, Monika, Marcelina, Magda, Milena, Marysia, Maria, Marta";
    }

    public Ranking(String name, List<String> tags) {
        this.rankingName = name;
        Collections.copy(this.tags,tags);

        //uzupelnienie listy imion na podstawie bazy
        this.names = null;
        namesString = "Kinga, Monika, Marcelina, Magda, Milena, Marysia, Maria";
    }

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
