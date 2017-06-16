package pl.edu.pw.mini.namefactory;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pw.mini.namefactory.DatabasePackage.DatabaseHandler;
import pl.edu.pw.mini.namefactory.Rankings.Ranking;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by Piotr on 16.06.2017.
 */

@RunWith(AndroidJUnit4.class)
public class DatabaseTests {
    private DatabaseHandler dbh;
    int rankingID = 0;

    @Before
    public void setUp() throws Exception {
        dbh = new DatabaseHandler(getTargetContext());
    }

    @Test
    public void NamesStorage() throws Exception {
        List<ApiName> names = new ArrayList<ApiName>();
        ApiName piotr = new ApiName();
        piotr.description = "omg hes soo awesome";
        piotr.id = 1;
        piotr.is_male = true;
        piotr.name = "Piotr";
        piotr.tags = null;
        ApiName adrian = new ApiName();
        adrian.description = "omg hes awesome too";
        adrian.id = 2;
        adrian.is_male = true;
        adrian.name = "Adrian";
        adrian.tags = null;
        names.add(piotr);
        names.add(adrian);

        dbh.pushNames(names);

        assertArrayEquals(new String[]{"Piotr", "omg hes soo awesome", "true"},dbh.getNameDetails(1));
        assertArrayEquals(new String[]{"Adrian", "omg hes awesome too", "true"},dbh.getNameDetails(2));
    }

    //sprawdzanie dodawania nowego rankingu i pobierania jego nazwy
    @Test
    public void NewRankingTest() throws Exception {
        int rankingIDnew = dbh.createRanking("boy");
        assertEquals(rankingID+1, rankingIDnew);
        rankingID = rankingIDnew;

        String rankingName = dbh.getRankingName(rankingID);
        assertEquals("boy", rankingName);
    }

    //sprawdzanie poprawnosci edycji nazwy rankingu
    @Test
    public void editRankingNameTest() throws Exception {
        NewRankingTest();

        dbh.editRankingName(rankingID, "boi");
        String rankingName = dbh.getRankingName(rankingID);

        assertEquals("boi", rankingName);
    }


    //sprawdzanie dodawania imion do rankingu i pobierania ich listy
    @Test
    public void pushNamesToRankingTest() throws Exception {
        NamesStorage();
        NewRankingTest();

        dbh.addNames2Ranking(rankingID, new int[]{1,2});
        List<String> expectedNames = new ArrayList<String>(){{
            add("Piotr");
            add("Adrian");
        }};

        assertThat(dbh.getNamesListAsString(rankingID), is(expectedNames));
    }


    //sprawdzanie zmiany wyniku danego imienia w danym rankingu i pobierania jego wartosci
    @Test
    public void updateScoreTest() throws Exception {
        NamesStorage();
        NewRankingTest();
        pushNamesToRankingTest();

        dbh.changeNamesScore(rankingID, 1, 100);
        double score = dbh.getNamesScore(rankingID, 1);

        assertEquals(100, score, 0.001);
    }


    //sprawdzanie pobierania listy ID wszystkich rankingów
    @Test
    public void getRankingsIDsListTest() throws Exception {
        NamesStorage();
        NewRankingTest();

        List<Integer> ids = dbh.getRankingsIDs();
        List<Integer> expectedIDs = new ArrayList<Integer>(){{
            add(1);
        }};
        assertThat(ids, is(expectedIDs));
    }


    //sprawdzanie zwracania ID danego rankingu - uwaga używanie tej metody jest niezalecane
    @Test
    public void getRankingNameTest() throws Exception {
        NamesStorage();
        NewRankingTest();
        editRankingNameTest();

        int id = dbh.getRankingsID("boi");

        assertEquals(rankingID, id);
    }
}
