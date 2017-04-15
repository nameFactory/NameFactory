package pl.edu.pw.mini.namefactory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class RankingView extends AppCompatActivity {

    private List<Name> namesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NamesAdapter nAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_view);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_for_names);

        nAdapter = new NamesAdapter(namesList, getApplicationContext());
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItem(this, LinearLayoutManager.VERTICAL));

        ItemTouchHelper.Callback callback =
                new SwipeHelperCallback(nAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(nAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //przejdz do aktywnosci namecard
                Intent in = new Intent(getApplicationContext(), NameCard.class);
                startActivity(in);

            }

            @Override
            public void onLongClick(View view, int position) {
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

        prepareNamesList();
    }

    //zczytywanie listy elementow z bazydanych LUB czegos innego
    private void prepareNamesList() {

        Name n1 = new Name("Kinga");
        namesList.add(n1);
        Name n2 = new Name("Monika");
        namesList.add(n2);
        Name n3 = new Name("Marcelina");
        namesList.add(n3);
        Name n4 = new Name("Magda");
        namesList.add(n4);
        Name n5 = new Name("Milena");
        namesList.add(n5);
        Name n6 = new Name("Marysia");
        namesList.add(n6);
        Name n7 = new Name("Maria");
        namesList.add(n7);
        Name n8 = new Name("Marta");
        namesList.add(n8);

        nAdapter.notifyDataSetChanged();
    }
}
