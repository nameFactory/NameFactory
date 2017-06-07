package pl.edu.pw.mini.namefactory;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pl.edu.pw.mini.namefactory.DatabasePackage.DatabaseHandler;
import pl.edu.pw.mini.namefactory.Dialogs.ChooseNameFragment;
import pl.edu.pw.mini.namefactory.Dialogs.ChooseRankingFragment;
import pl.edu.pw.mini.namefactory.JoinPackage.JoinRequest;
import pl.edu.pw.mini.namefactory.JoinPackage.JoinRequestFragment;
import pl.edu.pw.mini.namefactory.Names.ShowNamesFragment;
import pl.edu.pw.mini.namefactory.Rankings.*;
import pl.edu.pw.mini.namefactory.User.UserAccount;
import pl.edu.pw.mini.namefactory.User.UserProfileFragment;

public class RankingsListMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ChooseNameFragment.ChooseNameDialogListener,
        ChooseRankingFragment.ChooseRankingDialogListener, RankingsJoiningRequestFragment.OnRankingsJoiningRequestFragmentInteractionListener,
        EvaluationFragment.OnEvaluationFragmentInteractionListener, FiltersFragment.OnFiltersFragmentInteractionListener,
        ShowRankingsFragment.OnRankingsListFragmentInteractionListener, ShowNamesFragment.OnNamesListFragmentInteractionListener,
        NewRankingNameFragment.OnNewRankingFragmentInteractionListener, UserProfileFragment.OnUserFragmentInteractionListener,
        JoinRequestFragment.OnRequestListFragmentInteractionListener{

    public static DatabaseHandler dbh;
    public static ApiWrapper apiWrapper;
    public static ExecutorService fixedPool = Executors.newFixedThreadPool(1);
    private FragmentManager fm;
    private UserAccount User;
    public static int rankingNumber=0;
    //pobieranie rankingów gloablnych
    static ArrayList<String> GlobalNames = new ArrayList<>();
    public static ArrayList<Integer> GlobalIDs = new ArrayList<>();

    ProgressDialog loadDialog;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RankingsListMain.context = this;
        Log.i("MAIN","weszlo do onCreate RankingsListMain.");
        //sprawdzanie czy appka jest otwierana pierwszy raz i tworzenie na tej podstawie bazy danych
        databaseCheckFirstRun();



        //ustawienie Usera
        User = new UserAccount();
        User.setEmail("marta@ja.pl");
        User.setUserName("Marta");
        User.setName("Marta");

        setContentView(R.layout.activity_rankings_list_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fm = getSupportFragmentManager();
 /*       fm.addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        ((FloatingActionButton)findViewById(R.id.fab)).show();
                    }
                });*/



        //wyswietlenie fragmentu z lista rankingow
        //przejdz do rankingjoiningrequest
        FragmentTransaction ft = fm.beginTransaction();
        ShowRankingsFragment fragment = ShowRankingsFragment.newInstance();
        ft.replace(R.id.fragmentFrame, fragment, null)
                .commit();
    }

    private void databaseCheckFirstRun() {

        final String PREFS_NAME =  getResources().getString(R.string.shared_prefs_name);
        final String PREF_VERSION_CODE_KEY = "version_code";
        //final String PREF_LOGIN_CODE_KEY = "login";
        //final String PREF_PASS_CODE_KEY = "pass";
        final int DOESNT_EXIST = -1;
        final Context context = this;
        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;
        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        prefs.edit().remove(PREF_VERSION_CODE_KEY).commit();

        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {

            // This is just a normal run
            dbh = new DatabaseHandler(this);
            String login, password;
            try{
                Set<String> set = prefs.getStringSet("USER", null);
                if (set == null) throw new Exception();
                login = set.toArray(new String[2])[0];
                password = set.toArray(new String[2])[1];
                apiWrapper = new ApiWrapper(login, password);
            }
            catch(Exception e) {
                Toast.makeText(this, "No user was found", Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(this, "not a first run", Toast.LENGTH_LONG).show();
            return;

        } else if (savedVersionCode == DOESNT_EXIST) {

            // This is a new install (or the user cleared the shared preferences)
            dbh = new DatabaseHandler(this);

            loadDialog = ProgressDialog.show(RankingsListMain.this, "Loading", "Please wait while synchronising database...");
            final Handler handler = new Handler() {

                @Override
                public void handleMessage(Message msg) {
                    loadDialog.dismiss();
                }
            };

            Runnable newUserTask = new Runnable(){
                @Override
                public void run() {
                    SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                    try
                    {
                        ApiNewUser user = ApiWrapper.createNewUser(null);
                        String login = user.username;
                        String pass = user.password;

                        RankingsListMain.apiWrapper = new ApiWrapper(login, pass);

                        //zapis danych uzytkownika lokalnie
                        Set<String> set = new HashSet<>();
                        set.add(login);
                        set.add(pass);
                        prefs.edit().putStringSet("USER", set).apply();
                    }
                    catch(IOException e)
                    {
                        Log.i("suc", "Creating user unsuccessful");
                        //Toast.makeText(context, "Creating user unsuccessful", Toast.LENGTH_LONG).show();
                        return;
                    }
                    //dodawanie imion
                    try
                    {
                        List<ApiName> names = ApiWrapper.getNamesDB().names;
                        RankingsListMain.dbh.pushNames(names);
                        Log.i("suc", "Creating user successful");
                        //Toast.makeText(context, "Creating user successful", Toast.LENGTH_LONG).show();
                    }
                    catch(IOException e)
                    {
                        Log.i("suc", "Downloading names unsuccessful");
                        //Toast.makeText(context, "Downloading names unsuccessful", Toast.LENGTH_LONG).show();
                        return;
                    }
                    try {
                        List<ApiTopNames> topNamesList = ApiWrapper.getTop50().result;
                        for (ApiTopNames namesArray : topNamesList) {
                            String rankingName = namesArray.is_male() ? "chłopiec" : "dziewczynka";
                            int globalRankingID = dbh.createRanking(rankingName);
                            GlobalIDs.add(globalRankingID);
                            GlobalNames.add(rankingName);
                            dbh.addNames2Ranking(globalRankingID, namesArray.getTop50());
                        }

                    } catch (IOException e) {
                        Log.i("globalRanking", e.getMessage());
                        return;
                    }
                    handler.sendEmptyMessage(0);
                }
            };



            fixedPool.submit(newUserTask);
            Toast.makeText(this, "first run!", Toast.LENGTH_LONG).show();


        } else if (currentVersionCode > savedVersionCode) {

            // TODO This is an upgrade
            Toast.makeText(this, "upgrade!", Toast.LENGTH_LONG).show();
        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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


        } else if (id == R.id.nav_share) {
            //wybierz najpierw ktory ranking - dialog - lista
            // Creating Bundle object
            Bundle bundel = new Bundle();
            ArrayList<String> rankingsNames = dbh.getRankingsNames();
            ArrayList<Integer> rankingsIds = dbh.getRankingsIDs();

            // Storing data into bundle
            bundel.putStringArrayList("rankings", rankingsNames);
            bundel.putIntegerArrayList("rankingsID", rankingsIds);
            bundel.putSerializable("type", ChooseRankingFragment.RankingDialogType.CONNECTION);

            // Create an instance of the dialog fragment and show it
            DialogFragment dialog = new ChooseRankingFragment();
            dialog.setArguments(bundel);
            dialog.show(getSupportFragmentManager(), "ChooseRankingFragment");


        } else if(id == R.id.nav_profile)
        {
            //PRZEJSCIE DO FRAGMENTU USER PROFILE ________________________________________________________________
            UserProfileFragment setFragment= UserProfileFragment.newInstance(User.getUserName(), User.getEmail());
            fm.beginTransaction()
                    .replace(R.id.fragmentFrame, setFragment, null)
                    .addToBackStack(null)
                    .commit();
        }
        else if(id == R.id.nav_global)
        {
            // Creating Bundle object
            Bundle bundel = new Bundle();

            //globalne rankinig ___________________________________________________________

            // Storing data into bundle
            bundel.putStringArrayList("rankings", GlobalNames);
            bundel.putIntegerArrayList("rankingsID", GlobalIDs);
            bundel.putSerializable("type", ChooseRankingFragment.RankingDialogType.SHOWGLOBAL);

            //wybierz najpierw ktory ranking - dialog - lista
            // Create an instance of the dialog fragment and show it
            DialogFragment dialog = new ChooseRankingFragment();
            dialog.setArguments(bundel);
            dialog.show(getSupportFragmentManager(), "ChooseRankingFragment");

        } else if(id == R.id.nav_inbox){

            JoinRequestFragment setFragment= JoinRequestFragment.newInstance(1);
            fm.beginTransaction()
                    .replace(R.id.fragmentFrame, setFragment, null)
                    .addToBackStack(null)
                    .commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDialogRankingPositiveClick(DialogFragment dialog, Integer id, ChooseRankingFragment.RankingDialogType operationType) {


        //przejdz do rankingjoiningrequest
        FragmentTransaction ft = fm.beginTransaction();

        if(operationType== ChooseRankingFragment.RankingDialogType.CONNECTION)
        {

            RankingsJoiningRequestFragment fragment = RankingsJoiningRequestFragment.newInstance(id);
            ft.replace(R.id.fragmentFrame, fragment, null)
                    .addToBackStack(null)
                    .commit();
        }
        else if(operationType== ChooseRankingFragment.RankingDialogType.EVALUATION)
        {
            EvaluationFragment setFragment= EvaluationFragment.newInstance(id);
            ft.replace(R.id.fragmentFrame, setFragment, null)
                    .addToBackStack(null)
                    .commit();
        }
        else if(operationType== ChooseRankingFragment.RankingDialogType.SHOWGLOBAL)
        {
            //przejdz do fragmetnu shownamesfragment
            ShowNamesFragment setFragment= ShowNamesFragment.newInstance(id);
            ft.replace(R.id.fragmentFrame, setFragment, null)
                    .addToBackStack(null)
                    .commit();
        }


        //((FloatingActionButton)findViewById(R.id.fab)).hide();
    }

    @Override
    public void onDialogRankingNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    @Override
    public void onDialogNamePositiveClick(DialogFragment dialog, String name) {

        //((FloatingActionButton)findViewById(R.id.fab)).hide();
        // Creating Bundle object
        Bundle bundel = new Bundle();

        int id = dbh.getNamesID(name);

        // Storing data into bundle
        bundel.putInt("name", id);

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
    public void changeFloatingButtonDone() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        p.setAnchorId(View.NO_ID);
        fab.setLayoutParams(p);
        fab.setVisibility(View.VISIBLE);
        fab.setImageResource(R.drawable.ic_done_white_24dp);
        final Context context = this;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //dodawanie nowego rankingu
                //generowanie nazwy rankignu -------------------------------------------------
                String nameRanking = "generowana nazwa"+ Integer.toString(rankingNumber++);

                //dodawanie nowego rankingu
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
                final boolean gender =sharedPref.getBoolean("gender",false);
                final int rankingID = dbh.createRanking(nameRanking);
                //TODO tymczasowo dodajemy wszystkie imiona z bazy, dlatego przesyłamy null
                dbh.addNames2Ranking(rankingID, gender);

                Runnable newRankingTask = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            RankingsListMain.apiWrapper.createNewRanking(rankingID, gender, new int[0]);
                        } catch (IOException e) {
                            Toast.makeText(context, "Creating new ranking unsuccessful", Toast.LENGTH_LONG).show();
                        }
                    }
                };
                RankingsListMain.fixedPool.submit(newRankingTask);

                //przejdz do rankingjoiningrequest
                FragmentTransaction ft = fm.beginTransaction();
                ShowRankingsFragment fragment = ShowRankingsFragment.newInstance();
                ft.replace(R.id.fragmentFrame, fragment, null)
                        .commit();
            }
        });
    }

    @Override
    public void changeFloatingButtonAdd() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        p.setAnchorId(View.NO_ID);
        fab.setLayoutParams(p);
        fab.setVisibility(View.VISIBLE);
        fab.setImageResource(R.drawable.ic_add_white_48dp);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //PRZEJSCIE DO FRAGMENTU FILTERS ________________________________________________________________
                FiltersFragment setFragment= new FiltersFragment();
                fm.beginTransaction()
                        .replace(R.id.fragmentFrame, setFragment, null)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public void hideFloatingButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        p.setAnchorId(View.NO_ID);
        fab.setLayoutParams(p);
        fab.setVisibility(View.GONE);
        Log.i("MAIN","button hidden.");
    }

    @Override
    public void setTitleName(String name) {
        this.getSupportActionBar().setTitle(name);
    }
}
