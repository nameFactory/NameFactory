package pl.edu.pw.mini.namefactory.DatabasePackage;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.edu.pw.mini.namefactory.ApiName;
import pl.edu.pw.mini.namefactory.DatabasePackage.Database;

/**
 * Created by Piotr on 17.04.2017.
 */

public class DatabaseHandler {

    private Context context;
    private Database myDb;

    public DatabaseHandler(Context context){
        this.context = context;
        myDb = new Database(context, "localStorage");
    }
    //tworzenie nowej tabeli z podanymi imionami
    public void pushNames(List<ApiName> names){
        myDb.recreateNamesTable();
        for(ApiName name : names)
            myDb.insertData("NAMES", new String[]{name.name, name.description, String.valueOf(name.is_male)});
    }

    //wypisywanie opisu i nazwy danego imienia
    public String[] getNameDetails(int nameID){
        Cursor c = myDb.getNamesDetails(nameID);
        String[] result = new String[3];
        if (c != null) {
            if (c.moveToFirst()) {
                result[0] = c.getString(c.getColumnIndex("name"));
                result[1] = c.getString(c.getColumnIndex("desc"));
                result[2] = c.getString(c.getColumnIndex("male"));
            }
        }
        return result;
    }

    //tworzenie nowego rankingu
    public int createRanking(String rankingName){
        return myDb.insertData("RANKINGS", new String[]{rankingName});
    }

    //wypisywanie nazwy danego rankingu
    public String getRankingName(int nameID){
        Cursor c = myDb.getRankingName(nameID);
        String result = null;
        if (c != null) {
            if (c.moveToFirst()) {
                result = c.getString(c.getColumnIndex("name"));
            }
        }
        return result;
    }

    //zmiana nazwy rankingu
    public void editRankingName(int rankingID, String newRankingName){
        myDb.updateData("RANKINGS", rankingID, new String[]{newRankingName});
    }

    //dodawanie imion z danej listy id imion do danego rankingu
    public void addNames2Ranking(int rankingID, List<Integer> names)
    {
        //tymczasowo namesFromServer bo names jest null
        List<Integer> namesFromServer = new ArrayList<Integer>();

        Cursor c = myDb.getNamesIDs();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    Integer id = c.getInt(c.getColumnIndex("id"));
                    namesFromServer.add(id);
                } while (c.moveToNext());
            }
        }


        for (Integer id : namesFromServer)
            myDb.insertData("NAMES2RANKING", new String[] {Integer.toString(id), Integer.toString(rankingID), "0"});

    }

    //usuwanie rankingu
    public void deleteRanking(int rankingID){
        myDb.deleteRanking(rankingID);
    }


    //wypisywanie listy rankingów jako stringi
    public ArrayList<String> getRankingsNames(){
        Cursor c = myDb.getData("RANKINGS", new String[]{"name"});
        ArrayList<String> results = new ArrayList<String>();

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String name = c.getString(c.getColumnIndex("name"));
                    results.add(name);
                } while (c.moveToNext());
            }
        }
        return results;
    }

    //pobieranie id rankigu
    public int getRankingsID(String name){
        Cursor c = myDb.getRankingsID(name);
        int id = -1;
        if (c != null) {
            if (c.moveToFirst()) {
                id = c.getInt(c.getColumnIndex("id"));
            }
        }
        return id;
    }

    //wypisywanie listy rankingów
    public List<pl.edu.pw.mini.namefactory.Rankings.Ranking> getRankingList(){
        Cursor c = myDb.getData("RANKINGS", new String[]{"id", "name"});
        List<pl.edu.pw.mini.namefactory.Rankings.Ranking> results = new ArrayList<>();

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(c.getColumnIndex("id"));
                    String name = c.getString(c.getColumnIndex("name"));
                    results.add(new pl.edu.pw.mini.namefactory.Rankings.Ranking(name, id));
                } while (c.moveToNext());
            }
        }
        return results;
    }

    //zmiana score dla danego imienia w danym rankingu
    public void changeNamesScore(int rankingID, int nameID, double newScore){
        myDb.updateNamesScore(nameID, rankingID, newScore);
    }

    //wypisywanie aktualnego score dla imienia w danym rankingu
    public double getNamesScore(int rankingID, int nameID){
        Cursor c = myDb.getNameScore(nameID, rankingID);
        double currentScore = 0;
        if (c != null) {
            if (c.moveToFirst()) {
                currentScore = c.getDouble(c.getColumnIndex("score"));
            }
        }
        return currentScore;
    }

    //wypisywanie imion z danego rankingu
    public List<String> getNamesListAsString(int rankingID){
        Cursor c = myDb.getNamesFromRanking(rankingID);
        List<String> results = new ArrayList<>();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String name = c.getString(c.getColumnIndex("name"));
                    results.add(name);
                } while (c.moveToNext());
            }
        }
        return results;
    }

    //pobieranie id imienia
    public int getNamesID(String name){
        Cursor c = myDb.getNamesID(name);
        int id = -1;
        if (c != null) {
            if (c.moveToFirst()) {
                id = c.getInt(c.getColumnIndex("id"));
            }
        }
        return id;
    }

    //wypisywanie obiektów Name z danego rankingu
    public List<pl.edu.pw.mini.namefactory.Names.Name> getNameList(int rankingID){
        Cursor c = myDb.getNamesFromRanking(rankingID);
        List<pl.edu.pw.mini.namefactory.Names.Name> results = new ArrayList<>();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(c.getColumnIndex("id"));
                    String name = c.getString(c.getColumnIndex("name"));
                    boolean male = Boolean.parseBoolean(c.getString(c.getColumnIndex("male")));
                    results.add(new pl.edu.pw.mini.namefactory.Names.Name(id, name, male));
                } while (c.moveToNext());
            }
        }
        return results;
    }
}
