package pl.edu.pw.mini.namefactory;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import pl.edu.pw.mini.namefactory.DatabasePackage.DatabaseHandler;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewRankingFragment.OnNewRankingFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewRankingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewRankingFragment extends Fragment {
/*    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;*/

    EditText textBox;
    private DatabaseHandler dbh;

    private OnNewRankingFragmentInteractionListener mListener;

    public NewRankingFragment() {
        // Required empty public constructor
    }


    public static NewRankingFragment newInstance() {
        NewRankingFragment fragment = new NewRankingFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_ranking, container, false);
        view.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
        view.setClickable(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("New ranking");
        textBox = (EditText) view.findViewById(R.id.rankingName);

        final Button bt= (Button) view.findViewById(R.id.addBt);
        bt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                add(bt);
            }
        });
        this.dbh = RankingsListMain.dbh;

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNewRankingFragmentInteractionListener) {
            mListener = (OnNewRankingFragmentInteractionListener) context;
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
    public interface OnNewRankingFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }

    public void add(View v)
    {
        //dodawanie nowego rankingu
        String nameRanking = textBox.getText().toString();
        int rankingID = dbh.createRanking(nameRanking);

        //zamockowana lista imion wybranych z preferencji - null -----------------------------------
        dbh.addNames2Ranking(rankingID, null);

    }
}
