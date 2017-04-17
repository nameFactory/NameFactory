package pl.edu.pw.mini.namefactory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RankingsJoiningRequest extends AppCompatActivity {

    private String rankingName;
    private int rankingID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankings_joining_request);

        // get the Intent that started this Activity
        Intent in = getIntent();

        // get the Bundle that stores the data of this Activity
        Bundle b = in.getExtras();
        rankingID = b.getInt("rankingName");
        //ZMIEN----------------------------------------------------------------------------
        rankingName = "pierwszy" ;

        TextView textV = (TextView) findViewById(R.id.rankingNameText);
        textV.setText(rankingName);

    }

    public void sendRequest(View v)
    {
        //wyslanie prosby do polczaczenie rankingow
    }

}
