package pl.edu.pw.mini.namefactory;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.MediumTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.test.mock.MockContext;

import java.util.ArrayList;
import java.util.List;
import pl.edu.pw.mini.namefactory.DatabasePackage.DatabaseHandler;
import pl.edu.pw.mini.namefactory.Rankings.Ranking;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DataStorageTests {
    private DatabaseHandler dbh;
    int rankingID;

    @Before
    public void setUp() throws Exception {
        Context context = new MockContext();
        dbh = new DatabaseHandler(context);
    }

    //assertEquals(4, 2 + 2);
    //assertThat(sortedList.get(0)).isEqualTo(4);

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
            piotr.description = "omg hes awesome too";
            piotr.id = 2;
            piotr.is_male = true;
            piotr.name = "Adrian";
            piotr.tags = null;
        names.add(piotr);
        names.add(adrian);

        dbh.pushNames(names);

        assertArrayEquals(new String[]{"Piotr", "omg hes soo awesome", "true"},dbh.getNameDetails(1));
        assertArrayEquals(new String[]{"Adrian", "omg hes awesome too", "true"},dbh.getNameDetails(2));
    }

    //sprawdzanie dodawania nowego rankingu i pobierania jego nazwy
    @Test
    public void NewRankingTest() throws Exception {
        rankingID = dbh.createRanking("boy");
        assertEquals(1, rankingID);

        String rankingName = dbh.getRankingName(rankingID);
        assertEquals("boy", rankingName);
    }


    //sprawdzanie poprawnosci edycji nazwy rankingu
    @Test
    public void editRankingNameTest() throws Exception {
        dbh.editRankingName(rankingID, "boi");
        String rankingName = dbh.getRankingName(rankingID);

        assertEquals("boi", rankingName);
    }


    //sprawdzanie dodawania imion do rankingu i pobierania ich listy
    @Test
    public void pushNamesToRankingTest() throws Exception {
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
        dbh.changeNamesScore(rankingID, 1, 100);
        double score = dbh.getNamesScore(rankingID, 1);

        assertEquals(100, score, 0.001);
    }


    //sprawdzanie pobierania listy ID wszystkich rankingów
    @Test
    public void getRankingsIDsListTest() throws Exception {
        List<Integer> ids = dbh.getRankingsIDs();
        List<Integer> expectedIDs = new ArrayList<Integer>(){{
            add(1);
        }};
        assertThat(ids, is(expectedIDs));
    }


    //sprawdzanie zwracania ID danego rankingu - uwaga używanie tej metody jest niezalecane
    @Test
    public void getRankingNameTest() throws Exception {
        int id = dbh.getRankingsID("boi");

        assertEquals(1, id);
    }


    //sprawdzanie poprawnosci zwracania listy rankingów jako obiekty Ranking
    @Test
    public void getRankingsTest() throws Exception {
        List<Ranking> rankignList = dbh.getRankingList();
        List<Ranking> expectedList = new ArrayList<Ranking>(){{
            add(new Ranking(dbh, "boi",rankingID));
        }};

        assertThat(rankignList, is(expectedList));
    }
}