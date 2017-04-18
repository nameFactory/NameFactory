package pl.edu.pw.mini.namefactory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewRanking extends AppCompatActivity {

    String nameRanking;
    int rankingID;
    private DatabaseHandler dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ranking);

        EditText textBox = (EditText) findViewById(R.id.rankingName);
        nameRanking = textBox.getText().toString();
        this.dbh = RankingList.dbh;
    }

    public void add(View v)
    {
        rankingID = dbh.createRanking(nameRanking);
        //zamockowana lista imion wybranych z preferencji - null -----------------------------------
        dbh.addNames2Ranking(rankingID, null);

        Intent in = new Intent(getApplicationContext(), RankingList.class);
        startActivity(in);
    }
}
