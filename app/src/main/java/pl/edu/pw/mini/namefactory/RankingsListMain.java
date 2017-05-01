package pl.edu.pw.mini.namefactory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import pl.edu.pw.mini.namefactory.DatabasePackage.DatabaseHandler;
import pl.edu.pw.mini.namefactory.Dialogs.ChooseNameFragment;
import pl.edu.pw.mini.namefactory.Dialogs.ChooseRankingFragment;
import pl.edu.pw.mini.namefactory.Rankings.*;
import pl.edu.pw.mini.namefactory.Rankings.Ranking;

public class RankingsListMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ChooseNameFragment.ChooseNameDialogListener,
        ChooseRankingFragment.ChooseRankingDialogListener, RankingsJoiningRequestFragment.OnRankingsJoiningRequestFragmentInteractionListener,
        ShowRankingsFragment.OnRankingsListFragmentInteractionListener {

    public static DatabaseHandler dbh;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //----------------------------------------------------------------------------------------------------
        dbh = RankingList.dbh;
        //----------------------------------------------------------------------------------------------------

        setContentView(R.layout.activity_rankings_list_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //PRZEJSCIE DO FRAGMENTU FILTERS ________________________________________________________________
                FiltersFragment setFragment= new FiltersFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentFrame, setFragment, null)
                        .addToBackStack(null)
                        .commit();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fm = getSupportFragmentManager();

        //sprawdzanie czy appka jest otwierana pierwszy raz i tworzenie na tej podstawie bazy danych
       // databaseCheckFirstRun();

        //wyswietlenie fragmentu z lista rankingow
        //przejdz do rankingjoiningrequest
        FragmentTransaction ft = fm.beginTransaction();
        ShowRankingsFragment fragment = ShowRankingsFragment.newInstance();
        ft.replace(R.id.fragmentFrame, fragment, null)
                .addToBackStack(null)
                .commit();
    }

    private void databaseCheckFirstRun() {

        final String PREFS_NAME =  getResources().getString(R.string.shared_prefs_name);
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;

        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {

            // This is just a normal run
            dbh = new DatabaseHandler(this);
            Toast.makeText(this, "not a first run", Toast.LENGTH_LONG).show();
            return;

        } else if (savedVersionCode == DOESNT_EXIST) {

            // This is a new install (or the user cleared the shared preferences)
            dbh = new DatabaseHandler(this);
            //zamockowana lista imion---------------------------------------------------------------
            dbh.pushNames(null, null, null);
            Toast.makeText(this, "first run!", Toast.LENGTH_LONG).show();


        } else if (currentVersionCode > savedVersionCode) {

            // TODO This is an upgrade
            Toast.makeText(this, "upgrade!", Toast.LENGTH_LONG).show();
        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rankings_list_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_name) {
            // wybierz najpierw imie do wyszukania - dialog - wprowadzania tekstu
            // Create an instance of the dialog fragment and show it
            DialogFragment dialog = new ChooseNameFragment();
            dialog.show(getSupportFragmentManager(), "ChooseNameFragment");


        } else if (id == R.id.nav_new_ranking) {
            //przejdz do tworzenia nowego rankingu
            //PRZEJSCIE DO FRAGMENTU FILTERS ________________________________________________________________
            FiltersFragment setFragment= new FiltersFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentFrame, setFragment, null)
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_share) {
            //wybierz najpierw ktory ranking - dialog - lista
            // Creating Bundle object
            Bundle bundel = new Bundle();
            ArrayList<String> rankingsNames = dbh.getRankingsNames();

            // Storing data into bundle
            bundel.putStringArrayList("rankings", rankingsNames);

            // Create an instance of the dialog fragment and show it
            DialogFragment dialog = new ChooseRankingFragment();
            dialog.setArguments(bundel);
            dialog.show(getSupportFragmentManager(), "ChooseRankingFragment");


        } else if (id == R.id.nav_work_on) {

            // Creating Bundle object
            Bundle bundel = new Bundle();

            ArrayList<String> rankingsNames = dbh.getRankingsNames();

            // Storing data into bundle
            bundel.putStringArrayList("rankings", rankingsNames);

            //wybierz najpierw ktory ranking - dialog - lista
            // Create an instance of the dialog fragment and show it
            DialogFragment dialog = new ChooseRankingFragment();
            dialog.setArguments(bundel);
            dialog.show(getSupportFragmentManager(), "ChooseRankingFragment");

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDialogRankingPositiveClick(DialogFragment dialog, String name) {

        //przejdz do rankingjoiningrequest
        FragmentTransaction ft = fm.beginTransaction();

        //tutaj Id------------------------------------------------------------------------------------------
        int id = 7;

        RankingsJoiningRequestFragment fragment = RankingsJoiningRequestFragment.newInstance(id);
        ft.replace(R.id.fragmentFrame, fragment, null)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDialogRankingNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    @Override
    public void onDialogNamePositiveClick(DialogFragment dialog, String name) {

        // Creating Bundle object
        Bundle bundel = new Bundle();

        // Storing data into bundle
        bundel.putString("name", name);

        //przejdz do aktywnosci namecard
        Intent in = new Intent(getApplicationContext(), NameCard.class);
        in.putExtras(bundel);
        startActivity(in);
    }

    @Override
    public void onDialogNameNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    @Override
    public void onFragmentInteraction() {

    }

    @Override
    public void onRankingsListFragmentInteraction(Ranking item) {

    }
}
