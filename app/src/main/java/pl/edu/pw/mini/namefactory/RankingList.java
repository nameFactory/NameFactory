package pl.edu.pw.mini.namefactory;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RankingList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ChooseNameFragment.ChooseNameDialogListener,
        ChooseRankingFragment.ChooseNameDialogListener{

    private List<Ranking> rankingsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RankingsAdapter rAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), Filters.class);
                in.putExtra( PreferenceActivity.EXTRA_SHOW_FRAGMENT, Filters.FiltersPreferenceFragment.class.getName() );
                in.putExtra( PreferenceActivity.EXTRA_NO_HEADERS, true );
                startActivity(in);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        rAdapter = new RankingsAdapter(rankingsList, getApplicationContext());
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItem(this, LinearLayoutManager.VERTICAL));

        ItemTouchHelper.Callback callback =
                new SwipeHelperCallback(rAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(rAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                // Creating Bundle object
                Bundle bundel = new Bundle();

                // Storing data into bundle
                Ranking element = rankingsList.get(position);
                bundel.putInt("rankingName", element.getID());

                //przejdz do aktywnosci avaluation
                Intent in = new Intent(getApplicationContext(), Evaluation.class);
                in.putExtras(bundel);
                startActivity(in);

            }

            @Override
            public void onLongClick(View view, int position) {

                // Creating Bundle object
                Bundle bundel = new Bundle();

                // Storing data into bundle
                Ranking element = rankingsList.get(position);
                bundel.putInt("rankingName", element.getID());

                //przejdz do aktywnosci rankingview
                Intent in = new Intent(getApplicationContext(), RankingView.class);
                in.putExtras(bundel);
                startActivity(in);
                /*SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                editProductsOn = sharedPref.getBoolean("checkbox_edit_preference", true);

                //edycja elementu
                if(editProductsOn)
                {
                    ListPoint element = productsList.get(position);
                    DialogFragment dialog = EditDialogFragment.newInstance(position, element.getProductName(), element.getAmount());
                    dialog.show(getSupportFragmentManager(), "EditDialogFragment");
                }
                else
                    Toast.makeText(getApplicationContext(), "Edition is disabled.", Toast.LENGTH_SHORT).show();*/
            }

        }));

        prepareRankingsList();
    }

    //zczytywanie listy elementow z bazydanych LUB czegos innego
    private void prepareRankingsList() {

        //pobranie z bazy danych -----------------------------------------------------------------
        Ranking r1 = new Ranking();
        r1.setRankingName("Moj pierwszy ranking");
        rankingsList.add(r1);
        Ranking r2 = new Ranking();
        r2.setRankingName("Moj drugi ranking");
        rankingsList.add(r2);
        Ranking r3 = new Ranking();
        r3.setRankingName("Ranking probny");
        rankingsList.add(r3);
        Ranking r4 = new Ranking();
        r4.setRankingName("Wybor chlopca");
        rankingsList.add(r4);
        Ranking r5 = new Ranking();
        r5.setRankingName("Wybor dziewczynki");
        rankingsList.add(r5);


        rAdapter.notifyDataSetChanged();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ranking_list, menu);
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
    }

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
            Intent in = new Intent(getApplicationContext(), Filters.class);
            in.putExtra( PreferenceActivity.EXTRA_SHOW_FRAGMENT, Filters.FiltersPreferenceFragment.class.getName() );
            in.putExtra( PreferenceActivity.EXTRA_NO_HEADERS, true );
            startActivity(in);

        } else if (id == R.id.nav_share) {
            //wybierz najpierw ktory ranking - dialog - lista
            // Creating Bundle object
            Bundle bundel = new Bundle();

            //zmiana pobierz nazwy z bazy -----------------------------------------------------
            ArrayList<String> rankingsNames = new ArrayList<>(rankingsList.size());
            rankingsNames.add("Pierwszy");
            rankingsNames.add("Chlopcy");

            // Storing data into bundle
            bundel.putStringArrayList("rankings", rankingsNames);

            //wybierz najpierw ktory ranking - dialog - lista
            // Create an instance of the dialog fragment and show it
            DialogFragment dialog = new ChooseRankingFragment();
            dialog.setArguments(bundel);
            dialog.show(getSupportFragmentManager(), "ChooseRankingFragment");


        } else if (id == R.id.nav_work_on) {

            // Creating Bundle object
            Bundle bundel = new Bundle();

            //zmiana pobierz nazwy z bazy -----------------------------------------------------
            ArrayList<String> rankingsNames = new ArrayList<>(rankingsList.size());
            rankingsNames.add("Pierwszy");
            rankingsNames.add("Chlopcy");

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

    //zamknij dialog jesli nacisniety zostanie negatywny przycisk
    @Override
    public void onDialogNameNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    @Override
    public void onDialogRankingPositiveClick(DialogFragment dialog, String name) {

        // Creating Bundle object
        Bundle bundel = new Bundle();

        // Storing data into bundle
        bundel.putString("rankingName", name);

        //przejdz do aktywnosci rankingjoiningrequest
        Intent in = new Intent(getApplicationContext(), RankingsJoiningRequest.class);
        in.putExtras(bundel);
        startActivity(in);

    }

    //zamknij dialog jesli nacisniety zostanie negatywny przycisk
    @Override
    public void onDialogRankingNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }
}
