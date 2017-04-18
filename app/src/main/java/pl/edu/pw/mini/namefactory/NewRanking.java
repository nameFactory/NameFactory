package pl.edu.pw.mini.namefactory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewRanking extends AppCompatActivity {

    EditText textBox;

    int rankingID;
    private DatabaseHandler dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ranking);

        textBox = (EditText) findViewById(R.id.rankingName);
        this.dbh = RankingList.dbh;
    }

    public void add(View v)
    {
        String nameRanking = textBox.getText().toString();
        rankingID = dbh.createRanking(nameRanking);
        //Toast do testowania
        Toast.makeText(this, nameRanking + " " + Integer.toString(rankingID), Toast.LENGTH_LONG).show();
        //zamockowana lista imion wybranych z preferencji - null -----------------------------------
        dbh.addNames2Ranking(rankingID, null);

        Intent in = new Intent(getApplicationContext(), RankingList.class);
        startActivity(in);
    }
}
