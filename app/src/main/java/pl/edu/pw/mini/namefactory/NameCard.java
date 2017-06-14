package pl.edu.pw.mini.namefactory;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import pl.edu.pw.mini.namefactory.DatabasePackage.DatabaseHandler;

public class NameCard extends AppCompatActivity {

    private String name;
    private String desc;
    private boolean male;
    private int nameID;
    private DatabaseHandler dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.dbh = RankingsListMain.dbh;

        // get the Intent that started this Activity
        Intent in = getIntent();

        // get the Bundle that stores the data of this Activity
        Bundle b = in.getExtras();
        nameID =(int) b.get("name");
        String[] nameDetails = dbh.getNameDetails(nameID);
        name = nameDetails[0];
        desc = nameDetails[1];
        String paragraphs[] = desc.split("[\\r\\n]{2}");

        TextView descriptionBlock = (TextView) findViewById(R.id.nameDescription);
        descriptionBlock.setText(desc);

        if (nameDetails[2].equals("true")) male = true;
        else male = false;
        setTitle((CharSequence)nameDetails[0]);

        CollapsingToolbarLayout colToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        if(!male)
        {
            colToolbar.setBackgroundColor(getResources().getColor(R.color.colorGirl));
            colToolbar.setContentScrimColor(getResources().getColor(R.color.colorGirl));
            colToolbar.setStatusBarScrimColor(getResources().getColor(R.color.colorGirl));
        }
        else
        {
            colToolbar.setBackgroundColor(getResources().getColor(R.color.colorBoy));
            colToolbar.setContentScrimColor(getResources().getColor(R.color.colorBoy));
            colToolbar.setStatusBarScrimColor(getResources().getColor(R.color.colorBoy));
        }

    }
}
