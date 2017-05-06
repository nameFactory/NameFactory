package pl.edu.pw.mini.namefactory.Names;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import pl.edu.pw.mini.namefactory.Additional.ClickListener;
import pl.edu.pw.mini.namefactory.DatabasePackage.DatabaseHandler;
import pl.edu.pw.mini.namefactory.Additional.DividerItem;
import pl.edu.pw.mini.namefactory.NameCard;
import pl.edu.pw.mini.namefactory.R;
import pl.edu.pw.mini.namefactory.RankingList;
import pl.edu.pw.mini.namefactory.RankingsJoiningRequestFragment;
import pl.edu.pw.mini.namefactory.RankingsListMain;
import pl.edu.pw.mini.namefactory.Additional.RecyclerTouchListener;
import pl.edu.pw.mini.namefactory.Additional.SwipeHelperCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnNamesListFragmentInteractionListener}
 * interface.
 */
public class ShowNamesFragment extends Fragment {

    private static final String ARG_ID = "rankingID";
    // TODO: Customize parameters
    private OnNamesListFragmentInteractionListener mListener;

    private List<Name> namesList  = new ArrayList<>();;
    private NamesAdapter nAdapter;
    private String rankingName;
    private int rankingID = -1;
    private DatabaseHandler dbh;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShowNamesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ShowNamesFragment newInstance(int id) {

        Log.i("FRAG", "weszlo do newInstance w shownames");
        ShowNamesFragment fragment = new ShowNamesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.dbh = RankingList.dbh;
        setHasOptionsMenu(true);

        if (getArguments() != null) {

            rankingID = getArguments().getInt(ARG_ID);
            rankingName = dbh.getRankingName(rankingID);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_names_list, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(rankingName);
        Log.i("FRAG", "weszlo do onCreateView");
        RecyclerView recyclerView = (RecyclerView) view;
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            namesList = dbh.getNameList(rankingID);
            nAdapter = new NamesAdapter(namesList,context, mListener);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItem(context, LinearLayoutManager.VERTICAL));
            ItemTouchHelper.Callback callback =
                    new SwipeHelperCallback(nAdapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(recyclerView);
            recyclerView.setAdapter(nAdapter);

            recyclerView.addOnItemTouchListener(new RecyclerTouchListener( getActivity().getApplicationContext(), recyclerView, new ClickListener() {
                @Override
                public void onClick(View view, int position) {

                    // Creating Bundle object
                    Bundle bundel = new Bundle();

                    // Storing data into bundle
                    Name element = namesList.get(position);
                    bundel.putInt("name", element.getID());

                    //przejdz do aktywnosci namecard
                    Intent in = new Intent(getActivity().getApplicationContext(), NameCard.class);
                    in.putExtras(bundel);
                    startActivity(in);

                }

                @Override
                public void onLongClick(View view, int position) {

                }

            }));

            //Log.i("FRAG", "ustawiony adapter");
            //prepareNamesList();
            //Log.i("FRAG", "przygotowana lista");
        }
        return recyclerView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNamesListFragmentInteractionListener) {
            mListener = (OnNamesListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNamesListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //zczytywanie listy elementow z bazydanych LUB czegos innego
    private void prepareNamesList() {

        namesList = dbh.getNameList(rankingID);
        nAdapter.notifyDataSetChanged();
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
    public interface OnNamesListFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnNamesListFragmentInteractionListener(Name item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.ranking_view, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_connecting) {

            //przejdz do rankingjoiningrequest
            RankingsJoiningRequestFragment fragment = RankingsJoiningRequestFragment.newInstance(rankingID);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentFrame, fragment, null)
                    .addToBackStack(null)
                    .commit();

            //((FloatingActionButton) getView().findViewById(R.id.fab)).hide();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
