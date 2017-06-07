package pl.edu.pw.mini.namefactory;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Asus on 07.06.2017.
 */

public class GenderPreference extends Preference {
    public GenderPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GenderPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWidgetLayoutResource(R.layout.gender_preference);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        holder.itemView.setClickable(false); // disable parent click
        final View bbutton = holder.findViewById(R.id.boyGender);
        final View gbutton = holder.findViewById(R.id.girldGender);
        bbutton.setClickable(true); // enable custom view click
        bbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // persist your value here
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(RankingsListMain.context);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("gender", true);
                editor.commit();
                bbutton.setClickable(false);
                gbutton.setClickable(true);
                bbutton.setAlpha(.5f);
                gbutton.setAlpha(1);
            }
        });

        gbutton.setClickable(true); // enable custom view click
        gbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // persist your value here
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(RankingsListMain.context);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("gender", false);
                editor.commit();
                gbutton.setClickable(false);
                bbutton.setClickable(true);
                gbutton.setAlpha(.5f);
                bbutton.setAlpha(1);
            }
        });
        // the rest of the click binding
    }
}
