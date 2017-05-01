package pl.edu.pw.mini.namefactory;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.edu.pw.mini.namefactory.DatabasePackage.DatabaseHandler;


public class RankingsJoiningRequestFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ID = "rankingID";

    private String rankingName;
    private int rankingID;
    private DatabaseHandler dbh;
    private TextView textName;

    private OnRankingsJoiningRequestFragmentInteractionListener mListener;

    public RankingsJoiningRequestFragment() {
        // Required empty public constructor
    }


    public static RankingsJoiningRequestFragment newInstance(int id) {

        RankingsJoiningRequestFragment fragment = new RankingsJoiningRequestFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.dbh = RankingList.dbh;

        if (getArguments() != null) {

            rankingID = getArguments().getInt(ARG_ID);
            rankingName = dbh.getRankingName(rankingID);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rankings_joining_request, container, false);
        view.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
        view.setClickable(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Join your ranking");
        textName = (TextView) view.findViewById(R.id.rankingNameText);
        textName.setText(rankingName);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRankingsJoiningRequestFragmentInteractionListener) {
            mListener = (OnRankingsJoiningRequestFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnRankingsJoiningRequestFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }

    public void sendRequest(View v)
    {
        //wyslanie prosby do polczaczenie rankingow
    }
}
