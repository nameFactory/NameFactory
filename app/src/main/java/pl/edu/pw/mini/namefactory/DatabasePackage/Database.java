package pl.edu.pw.mini.namefactory.DatabasePackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Piotr on 17.04.2017.
 */

public class Database extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private static Context context;
    private static String DatabaseName;
    private Map<String, List<String>> tablesColumns;

    public Database(Context context, String name) {
        super(context, DatabaseName = name, null, 1);
        this.context = context;
        db = this.getWritableDatabase();
        createColumnsList();

        onUpgrade(db, 1, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table NAMES (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, desc TEXT, male BOOLEAN)");

        db.execSQL("create table TAGS (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");

        db.execSQL("create table NAMES2TAG (id_tag INTEGER, id_name INTEGER," +
                "FOREIGN KEY(id_tag) REFERENCES TAGS(id), FOREIGN KEY(id_name) REFERENCES NAMES(id), PRIMARY KEY (id_tag, id_name))");

        db.execSQL("create table RANKINGS (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");

        db.execSQL("create table NAMES2RANKING (id_name INTEGER, id_ranking INTEGER, score REAL," +
                "FOREIGN KEY(id_ranking) REFERENCES RANKINGS(id), FOREIGN KEY(id_name) REFERENCES NAMES(id), PRIMARY KEY (id_name, id_ranking))");

        db.execSQL("create table TAGS2RANKING (id_tag INTEGER, id_ranking INTEGER," +
                "FOREIGN KEY(id_tag) REFERENCES TAGS(id), FOREIGN KEY(id_ranking) REFERENCES RANKINGS(id), PRIMARY KEY (id_tag, id_ranking))");

        db.execSQL("create table PAIRSQUEUE (id_name1 INTEGER, id_name2 INTEGER, id_ranking INTEGER," +
                "FOREIGN KEY(id_name1) REFERENCES NAMES(id), FOREIGN KEY(id_name2) REFERENCES NAMES(id), FOREIGN KEY(id_ranking) REFERENCES RANKINGS(id), PRIMARY KEY (id_name1, id_name2, id_ranking))");

        db.execSQL("create table SCOREDPAIRS (id_winner INTEGER, id_loser INTEGER, id_ranking INTEGER," +
                "FOREIGN KEY(id_winner) REFERENCES NAMES(id), FOREIGN KEY(id_loser) REFERENCES NAMES(id), FOREIGN KEY(id_ranking) REFERENCES RANKINGS(id), PRIMARY KEY (id_winner, id_loser, id_ranking))");

        db.execSQL("create table USERS (uuid INTEGER PRIMARY KEY, nickname TEXT, fullname TEXT, secret TEXT)");

        db.execSQL("create table COMMONRANKINGS (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, id_user1 INTEGER, id_ranking_user1 INTEGER, id_user2 INTEGER, id_ranking_user2 INTEGER, " +
                "FOREIGN KEY(id_user1) REFERENCES USERS(uuid), FOREIGN KEY(id_user2) REFERENCES USERS(uuid), FOREIGN KEY(id_ranking_user1) REFERENCES RANKINGS(id), FOREIGN KEY(id_ranking_user2) REFERENCES RANKINGS(id))");

    }

    private void createColumnsList(){
        tablesColumns = new HashMap<String, List<String>>();

        List<String> columns = Arrays.asList("name", "desc", "male");
        tablesColumns.put("NAMES", columns);

        columns = Arrays.asList("name");
        tablesColumns.put("TAGS", columns);

        columns = Arrays.asList("id_tag", "id_name");
        tablesColumns.put("NAMES2TAG", columns);

        columns = Arrays.asList("name");
        tablesColumns.put("RANKINGS", columns);

        columns = Arrays.asList("id_name", "id_ranking", "score");
        tablesColumns.put("NAMES2RANKING", columns);

        columns = Arrays.asList("id_tag", "id_ranking");
        tablesColumns.put("TAGS2RANKING", columns);

        columns = Arrays.asList("id_name1", "id_name2", "id_ranking");
        tablesColumns.put("PAIRSQUEUE", columns);

        columns = Arrays.asList("id_winner", "id_loser", "id_ranking");
        tablesColumns.put("SCOREDPAIRS", columns);

        columns = Arrays.asList("uuid", "nickname", "fullname", "secret");
        tablesColumns.put("USERS", columns);

        columns = Arrays.asList("name", "id_user1", "id_ranking_user1", "id_user2", "id_ranking_user2");
        tablesColumns.put("COMMONRANKINGS", columns);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(String str : tablesColumns.keySet())
            db.execSQL("DROP TABLE IF EXISTS " + str);

        onCreate(db);
    }
    public void recreateNamesTable(){
        db.execSQL("DROP TABLE IF EXISTS NAMES");
        db.execSQL("create table NAMES (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, desc TEXT, male BOOLEAN)");
    }

    public Cursor getData(String tableName, String[] columns){
        StringBuilder builder = new StringBuilder();

        for (String string : columns) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append(string);
        }

        //chyba przecinek zostaje na końcu
        String columnsString = builder.toString();

        Cursor res = db.rawQuery("select " + columnsString + " from " + tableName, null);
        return res;
    }

    public Cursor getNamesFromRanking(int rankingID){
        Cursor res = db.rawQuery("SELECT n.name, n.id, n.male, nr.score from NAMES n JOIN NAMES2RANKING nr ON n.id = nr.id_name WHERE nr.id_ranking = ? ORDER BY nr.score DESC", new String[] {Integer.toString(rankingID)});
        return res;
    }

    public Cursor getNamesDetails(int nameID){
        Cursor res = db.rawQuery("SELECT name, desc, male FROM NAMES WHERE id = ?", new String[] {Integer.toString(nameID)});
        return res;
    }

    public Cursor getNamesIDs(){
        Cursor res = db.rawQuery("SELECT id FROM NAMES", new String[0]);
        return res;
    }

    public Cursor getRankingName(int rankingID){
        Cursor res = db.rawQuery("SELECT name FROM RANKINGS WHERE id = ?", new String[] {Integer.toString(rankingID)});
        return res;
    }

    //uwaga trzeba samemu pilnować kolejności i poprawności wartości kolumn, nie podajemy kolumn autoincrementowanych
    public int insertData(String tableName, String[] values){
        ContentValues contentValues = new ContentValues();
        int i = 0;
        for(String str: values) contentValues.put(tablesColumns.get(tableName).get(i++), str);

        long result = db.insert(tableName, null, contentValues);

        return (int)result;
    }

    public boolean updateData(String tableName, int id, String[] values){

        ContentValues contentValues = new ContentValues();
        int i = 0;
        for(String str: values) contentValues.put(tablesColumns.get(tableName).get(i++), str);

        db.update(tableName, contentValues, "id = ?", new String[] {Integer.toString(id)});
        return true;
    }

    public boolean updateNamesScore(int idName, int idRanking, double newScore){

        ContentValues contentValues = new ContentValues();
        contentValues.put("id_name", idName);
        contentValues.put("id_ranking", idRanking);
        contentValues.put("score", newScore);

        db.update("NAMES2RANKING", contentValues, "id_name = ? AND id_ranking = ?", new String[] {Integer.toString(idName), Integer.toString(idRanking)});
        return true;
    }

    public Cursor getNameScore(int idName, int idRanking){
        Cursor res = db.rawQuery("SELECT score FROM NAMES2RANKING WHERE id_name = ? AND id_ranking = ?", new String[] {Integer.toString(idName), Integer.toString(idRanking)});
        return res;
    }

    public boolean deleteData(String tableName, int id){
        db.delete(tableName, "id = ?", new String[] {Integer.toString(id)});
        return true;
    }

    public boolean deleteRanking(int id){
        deleteData("RANKINGS", id);

        db.delete("NAMES2RANKING", "id_ranking = ?", new String[] {Integer.toString(id)});
        db.delete("TAGS2RANKING", "id_ranking = ?", new String[] {Integer.toString(id)});
        return true;
    }
}

