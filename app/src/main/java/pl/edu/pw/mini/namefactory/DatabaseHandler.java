package pl.edu.pw.mini.namefactory;

import android.content.Context;
import android.database.Cursor;
import android.widget.TextView;

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
    //dodawanie imion do bazy z listy pobranej z serwera
    public void pushNames(List<String> names, List<String> desc, List<Boolean> male){

        List<String> namesFromServer = Arrays.asList("Piotr", "Michał", "Kuba", "Marek", "Alex");
        List<String> descFromServer = Arrays.asList("fancy name", "shitty name", "fag name", "marek lowca szparek", "to jest opis");
        List<Boolean> boolsFromServer = Arrays.asList(true, true, true, true, true);

        for (int i = 0; i<namesFromServer.size(); i++)
            myDb.insertData("NAMES", new String[]{namesFromServer.get(i), descFromServer.get(i), boolsFromServer.get(i).toString()});
    }

    //wypisywanie opisu danego imienia
    public String getDesc(int nameID){
        Cursor c = myDb.getNamesDesc(nameID);
        String result = null;
        if (c != null) {
            if (c.moveToFirst()) {
                result = c.getString(c.getColumnIndex("desc"));
            }
        }
        return result;
    }

    //tworzenie nowego rankingu
    public int createRanking(String rankingName){
        return myDb.insertData("RANKINGS", new String[]{rankingName});
    }

    //zmiana nazwy rankingu
    public void editRankingName(int rankingID, String newRankingName){
        myDb.updateData("RANKINGS", rankingID, new String[]{newRankingName});
    }

    //dodawanie imion z danej listy id imion do danego rankingu
    public void addNames2Ranking(int rankingID, List<String> names){
        for (String str : names)
            myDb.insertData("NAMES2RANKING", new String[] {str, Integer.toString(rankingID), "0"});

    }

    //usuwanie rankingu
    public void deleteRanking(int rankingID){
        myDb.deleteRanking(rankingID);
    }

    //wypisywanie listy rankingów
    public List<Ranking> getRankingList(){
        Cursor c = myDb.getData("RANKINGS", new String[]{"id", "name"});
        List<Ranking> results = new ArrayList<Ranking>();

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(c.getColumnIndex("id"));
                    String name = c.getString(c.getColumnIndex("name"));
                    results.add(new Ranking(this, name, id));
                } while (c.moveToNext());
            }
        }
        return results;
    }

    //zmiana score dla danego imienia w danym rankingu
    public void changeNamesScore(int rankingID, int nameID, int newScore){
        myDb.updateNamesScore(nameID, rankingID, newScore);
    }

    //wypisywanie imion i punktów z danego rankingu
    public List<String> getNamesList(int rankingID){
        Cursor c = myDb.getNamesFromRanking(rankingID);
        List<String> results = new ArrayList<String>();
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
}
