package pl.edu.pw.mini.namefactory;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class Evaluation extends AppCompatActivity {

    private TextSwitcher n1Switcher;
    private TextSwitcher n2Switcher;
    private int addedScore = 100;
    private String rankingName;
    private int rankingID;
    private DatabaseHandler dbh;

    // Array of String to Show In TextSwitcher
    private Name[] namesToShow;
    int messageCount;
    // to keep current Index of text
    int currentIndex=-1;
    int ind1;
    int ind2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        // get the Intent that started this Activity
        Intent intent = getIntent();

        this.dbh = RankingList.dbh;

        // get the Bundle that stores the data of this Activity
        Bundle b = intent.getExtras();
        rankingID = b.getInt("rankingName");
        rankingName = dbh.getRankingName(rankingID);

        setTitle((CharSequence)rankingName );

        namesToShow = dbh.getNameList(rankingID).toArray(new Name[0]);
        messageCount=namesToShow.length;

        // get The references
        n1Switcher = (TextSwitcher) findViewById(R.id.name1);
        n2Switcher = (TextSwitcher) findViewById(R.id.name2);

        // Set the ViewFactory of the TextSwitcher that will create TextView object when asked
        n1Switcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                // TODO Auto-generated method stub
                // create new textView and set the properties like clolr, size etc
                TextView myText = new TextView(Evaluation.this);
                myText.setGravity(Gravity.CENTER);
                myText.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                myText.setTextSize(36);
                myText.setTextColor(Color.WHITE);
                return myText;
            }
        });
        n2Switcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                // TODO Auto-generated method stub
                // create new textView and set the properties like clolr, size etc
                TextView myText = new TextView(Evaluation.this);
                myText.setGravity(Gravity.CENTER);
                myText.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                myText.setTextSize(36);
                myText.setTextColor(Color.WHITE);
                return myText;
            }
        });

        // Declare the in and out animations and initialize them
        Animation in = AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right);

        // set the animation type of textSwitcher
        n1Switcher.setInAnimation(in);
        n1Switcher.setOutAnimation(out);
        n2Switcher.setInAnimation(in);
        n2Switcher.setOutAnimation(out);

        ChooseDataForSwitchers();

/*        // ClickListener for NEXT button
        // When clicked on Button TextSwitcher will switch between texts
        // The current Text will go OUT and next text will come in with specified animation
        btnNext.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

            }
        });*/
    }

    //nacisniecie pierwszego imienia (ind1)
    public void switchNames(View v)
    {
        if(v.getId()==n1Switcher.getId())
        {
            //wybrane imie ma tutaj ind1
            int currentScore = dbh.getNamesScore(rankingID, namesToShow[ind1].getID());
            dbh.changeNamesScore(rankingID,namesToShow[ind1].getID(), currentScore, addedScore);
            Toast.makeText(this, "gora " + Integer.toString(currentScore), Toast.LENGTH_LONG).show();
        }
        else if (v.getId() == n2Switcher.getId())
        {
            //wybrane imie ma tutaj ind2
            int currentScore = dbh.getNamesScore(rankingID, namesToShow[ind1].getID());
            dbh.changeNamesScore(rankingID,namesToShow[ind2].getID(), currentScore, addedScore);
            Toast.makeText(this, "dol " + Integer.toString(currentScore), Toast.LENGTH_LONG).show();
        }

        ChooseDataForSwitchers();

    }

    private void ChooseDataForSwitchers()
    {
        //LOSOWANIE _-------------------------------------
        currentIndex++;
        ind1=currentIndex;
        // If index reaches maximum reset it
        if(currentIndex==messageCount)
            currentIndex=0;
        n1Switcher.setText(namesToShow[currentIndex].getName());

        currentIndex++;
        ind2 = currentIndex;
        // If index reaches maximum reset it
        if(currentIndex==messageCount)
            currentIndex=0;
        n2Switcher.setText(namesToShow[currentIndex].getName());
    }
}
