package pl.edu.pw.mini.namefactory;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class NameCard extends AppCompatActivity {

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get the Intent that started this Activity
        Intent in = getIntent();

        // get the Bundle that stores the data of this Activity
        Bundle b = in.getExtras();
        name =(String) b.get("name");
        setTitle((CharSequence)name );

        CollapsingToolbarLayout colToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        colToolbar.setBackgroundColor(getResources().getColor(R.color.colorGirl));
        colToolbar.setContentScrimColor(getResources().getColor(R.color.colorGirl));
        colToolbar.setStatusBarScrimColor(getResources().getColor(R.color.colorGirl));

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }
}
