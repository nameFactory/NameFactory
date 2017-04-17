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
    private List<String> namesFromServer;

    public DatabaseHandler(Context context){
        this.context = context;
        myDb = new Database(context, "localStorage");
    }
    //dodawanie imion do bazy z listy pobranej z serwera
    public void pushNames(List<String> names){
        namesFromServer = Arrays.asList("Piotr", "Michał", "Kuba", "Marek", "Alex");

        for (String str : namesFromServer)
            myDb.insertData("NAMES", new String[]{str, "fancy name", "true"});
    }


}
