package pl.edu.pw.mini.namefactory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewRanking extends AppCompatActivity {

    String nameRanking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ranking);

        EditText textBox = (EditText) findViewById(R.id.rankingName);
        nameRanking = textBox.getText().toString();
    }

    public void add(View v)
    {
        //dodanie nowego rankingu-----------------------------------------------------
    }
}
