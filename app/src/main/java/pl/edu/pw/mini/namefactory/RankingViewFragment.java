package pl.edu.pw.mini.namefactory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.edu.pw.mini.namefactory.Additional.ClickListener;
import pl.edu.pw.mini.namefactory.Additional.DividerItem;
import pl.edu.pw.mini.namefactory.Additional.RecyclerTouchListener;
import pl.edu.pw.mini.namefactory.Additional.SwipeHelperCallback;
import pl.edu.pw.mini.namefactory.DatabasePackage.DatabaseHandler;
import pl.edu.pw.mini.namefactory.Names.Name;
import pl.edu.pw.mini.namefactory.Names.NamesAdapter;
import pl.edu.pw.mini.namefactory.Names.ShowNamesFragment;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class RankingViewFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_ID = "rankingID";
    // TODO: Customize parameters
    private ShowNamesFragment.OnNamesListFragmentInteractionListener mListener;

    private List<Name> namesList;
    private RecyclerView recyclerView;
    private NamesAdapter nAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String rankingName;
    private int rankingID = -1;
    private DatabaseHandler dbh;
    private FragmentManager fm;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RankingViewFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RankingViewFragment newInstance(int id) {
        RankingViewFragment fragment = new RankingViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.dbh = RankingList.dbh;
        //fm = getSupportFragmentManager();

        if (getArguments() != null) {
            rankingID = getArguments().getInt(ARG_ID);
            rankingName = dbh.getRankingName(rankingID);
            //namesList = new ArrayList<>(1);
            //setTitle((CharSequence)rankingName );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking_view, container, false);

        final Context context = view.getContext();
        nAdapter = new NamesAdapter(namesList,context, mListener);


        // Set the adapter
        if (view instanceof RecyclerView) {


            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItem(context, LinearLayoutManager.VERTICAL));
            ItemTouchHelper.Callback callback =
                    new SwipeHelperCallback(nAdapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(recyclerView);
            recyclerView.setAdapter(nAdapter);

            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, recyclerView, new ClickListener() {
                @Override
                public void onClick(View view, int position) {

                    // Creating Bundle object
                    Bundle bundel = new Bundle();

                    // Storing data into bundle
                    Name element = namesList.get(position);
                    bundel.putInt("name", element.getID());

                    //przejdz do aktywnosci namecard
                    Intent in = new Intent(context, NameCard.class);
                    in.putExtras(bundel);
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

        return view;
    }

    //zczytywanie listy elementow z bazydanych LUB czegos innego
    private void prepareNamesList() {

        namesList = dbh.getNameList(rankingID);


//        Name n1 = new Name("Kinga");
//        namesList.add(n1);
//        Name n2 = new Name("Monika");
//        namesList.add(n2);
//        Name n3 = new Name("Marcelina");
//        namesList.add(n3);
//        Name n4 = new Name("Magda");
//        namesList.add(n4);
//        Name n5 = new Name("Milena");
//        namesList.add(n5);
//        Name n6 = new Name("Marysia");
//        namesList.add(n6);
//        Name n7 = new Name("Maria");
//        namesList.add(n7);
//        Name n8 = new Name("Marta");
//        namesList.add(n8);

        nAdapter.notifyDataSetChanged();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (ShowNamesFragment.OnNamesListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRankingsListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(pl.edu.pw.mini.namefactory.Names.Name name);
    }
}
