package pl.edu.pw.mini.namefactory;

import android.content.Context;
import android.database.Cursor;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public void pushNames(List<String> names, List<String> desc, List<Boolean> male){

        List<String> namesFromServer = Arrays.asList("Piotr", "Michał", "Kuba", "Marek", "Alex");
        List<String> descFromServer = Arrays.asList("fancy name desc", "fancy name desc", "fancy name desc", "fancy name desc", "fancy name desc");
        List<Boolean> boolsFromServer = Arrays.asList(true, true, true, true, true);

        myDb.recreateNamesTable();
        for (int i = 0; i<namesFromServer.size(); i++)
            myDb.insertData("NAMES", new String[]{namesFromServer.get(i), descFromServer.get(i), boolsFromServer.get(i).toString()});
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
    public void addNames2Ranking(int rankingID, List<String> names)
    {
        List<Integer> namesFromServer = Arrays.asList(1, 2, 3, 4, 5);
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


    //wypisywanie listy rankingów
    public List<Ranking> getRankingList(){
        Cursor c = myDb.getData("RANKINGS", new String[]{"id", "name"});
        List<Ranking> results = new ArrayList<>();

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(c.getColumnIndex("id"));
                    String name = c.getString(c.getColumnIndex("name"));
                    results.add(new Ranking(name, id));
                } while (c.moveToNext());
            }
        }
        return results;
    }

    //zmiana score dla danego imienia w danym rankingu
    public void changeNamesScore(int rankingID, int nameID, int currentScore, int Score){
        int newScore = currentScore + Score;
        myDb.updateNamesScore(nameID, rankingID, newScore);
    }

    //wypisywanie aktualnego score dla imienia w danym rankingu
    public int getNamesScore(int rankingID, int nameID){
        Cursor c = myDb.getNameScore(nameID, rankingID);
        int currentScore = 0;
        if (c != null) {
            if (c.moveToFirst()) {
                currentScore = c.getInt(c.getColumnIndex("score"));
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

    //wypisywanie obiektów Name z danego rankingu
    public List<Name> getNameList(int rankingID){
        Cursor c = myDb.getNamesFromRanking(rankingID);
        List<Name> results = new ArrayList<>();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(c.getColumnIndex("id"));
                    String name = c.getString(c.getColumnIndex("name"));
                    results.add(new Name(id, name));
                } while (c.moveToNext());
            }
        }
        return results;
    }
}
