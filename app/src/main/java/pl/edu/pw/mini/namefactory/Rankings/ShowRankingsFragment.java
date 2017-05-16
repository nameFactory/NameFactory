package pl.edu.pw.mini.namefactory.Rankings;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pw.mini.namefactory.Additional.ClickListener;
import pl.edu.pw.mini.namefactory.DatabasePackage.DatabaseHandler;
import pl.edu.pw.mini.namefactory.Additional.DividerItem;
import pl.edu.pw.mini.namefactory.EvaluationFragment;
import pl.edu.pw.mini.namefactory.Names.ShowNamesFragment;
import pl.edu.pw.mini.namefactory.R;
import pl.edu.pw.mini.namefactory.RankingsListMain;
import pl.edu.pw.mini.namefactory.Additional.RecyclerTouchListener;
import pl.edu.pw.mini.namefactory.Additional.SwipeHelperCallback;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnRankingsListFragmentInteractionListener}
 * interface.
 */
public class ShowRankingsFragment extends Fragment {

    private List<Ranking> rankingsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private OnRankingsListFragmentInteractionListener mListener;
    private RankingsAdapter rAdapter;
    private DatabaseHandler dbh;
    //private FragmentManager fm;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShowRankingsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ShowRankingsFragment newInstance() {

        ShowRankingsFragment fragment = new ShowRankingsFragment();
       /* Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }*/
        this.dbh = RankingsListMain.dbh;
        //fm = getActivity().getSupportFragmentManager();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rankings_list, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("NameFactory");
        recyclerView = (RecyclerView) view;
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            rankingsList = dbh.getRankingList();
            rAdapter = new RankingsAdapter(rankingsList, context, mListener);
            //RecyclerView recyclerView = (RecyclerView) view;

            //if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new GridLayoutManager(context,2));
            //} else {
            //    recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            //}

            recyclerView.setItemAnimator(new DefaultItemAnimator());
            //recyclerView.addItemDecoration(new DividerItem(context, LinearLayoutManager.VERTICAL));
            ItemTouchHelper.Callback callback =
                    new SwipeHelperCallback(rAdapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(recyclerView);
            recyclerView.setAdapter(rAdapter);

            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new ClickListener() {
                @Override
                public void onClick(View view, int position) {

                    Ranking element = rankingsList.get(position);

                    //przejdz do fragmetnu evaluation
                    EvaluationFragment setFragment= EvaluationFragment.newInstance(element.getID());
                     getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentFrame, setFragment, null)
                            .addToBackStack(null)
                            .commit();

                    //((FloatingActionButton) getView().findViewById(R.id.fab)).hide();

                }

                @Override
                public void onLongClick(View view, int position) {

                    Ranking element = rankingsList.get(position);

                    //przejdz do fragmetnu shownamesfragment
                    ShowNamesFragment setFragment= ShowNamesFragment.newInstance(element.getID());
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentFrame, setFragment, null)
                            .addToBackStack(null)
                            .commit();

                    //((FloatingActionButton)getView().findViewById(R.id.fab)).hide();
                }

            }));

           // prepareRankingsList();
        }
        return recyclerView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRankingsListFragmentInteractionListener) {
            mListener = (OnRankingsListFragmentInteractionListener) context;
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

    //zczytywanie listy elementow z bazydanych LUB czegos innego
    private void prepareRankingsList() {

        rankingsList = dbh.getRankingList();
        rAdapter.notifyDataSetChanged();
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
    public interface OnRankingsListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onRankingsListFragmentInteraction(Ranking item);
    }
}
