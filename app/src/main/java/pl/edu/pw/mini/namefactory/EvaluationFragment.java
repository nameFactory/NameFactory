package pl.edu.pw.mini.namefactory;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import pl.edu.pw.mini.namefactory.DatabasePackage.DatabaseHandler;



public class EvaluationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ID = "rankingID";

    private TextSwitcher n1Switcher;
    private TextSwitcher n2Switcher;
    private int addedScore = 100;
    private String rankingName;
    private int rankingID;
    private DatabaseHandler dbh;

    // Array of String to Show In TextSwitcher
    private pl.edu.pw.mini.namefactory.Names.Name[] namesToShow;
    int messageCount;
    // to keep current Index of text
    int currentIndex=-1;
    int ind1;
    int ind2;

    private OnEvaluationFragmentInteractionListener mListener;

    public EvaluationFragment() {
        // Required empty public constructor
    }

    public static EvaluationFragment newInstance(int id) {

        Log.i("FRAG", "weszlo do newInstance");
        EvaluationFragment fragment = new EvaluationFragment();
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
            namesToShow = dbh.getNameList(rankingID).toArray(new pl.edu.pw.mini.namefactory.Names.Name[0]);
            messageCount=namesToShow.length;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_evaluation, container, false);
        view.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
        view.setClickable(true);

        n1Switcher = (TextSwitcher) view.findViewById(R.id.name1);
        n2Switcher = (TextSwitcher) view.findViewById(R.id.name2);

        // Set the ViewFactory of the TextSwitcher that will create TextView object when asked
        n1Switcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                // TODO Auto-generated method stub
                // create new textView and set the properties like clolr, size etc
                TextView myText = new TextView(getActivity());
                myText.setGravity(Gravity.CENTER);
                myText.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                myText.setTextSize(36);
                myText.setTextColor(Color.WHITE);
                return myText;
            }
        });
        n2Switcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                // TODO Auto-generated method stub
                // create new textView and set the properties like clolr, size etc
                TextView myText = new TextView(getActivity());
                myText.setGravity(Gravity.CENTER);
                myText.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                myText.setTextSize(36);
                myText.setTextColor(Color.WHITE);
                return myText;
            }
        });

        // Declare the in and out animations and initialize them
        Animation in = AnimationUtils.loadAnimation(getActivity(),android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getActivity(),android.R.anim.slide_out_right);

        // set the animation type of textSwitcher
        n1Switcher.setInAnimation(in);
        n1Switcher.setOutAnimation(out);
        n2Switcher.setInAnimation(in);
        n2Switcher.setOutAnimation(out);

        ChooseDataForSwitchers();
        Log.i("FRAG", "weszlo do onCreateView");

        return view;
    }

    private void ChooseDataForSwitchers()
    {
        //LOSOWANIE _-------------------------------------
        currentIndex++;
        // If index reaches maximum reset it
        if(currentIndex==messageCount)
            currentIndex=0;
        ind1=currentIndex;
        n1Switcher.setText(namesToShow[currentIndex].getName());

        currentIndex++;
        // If index reaches maximum reset it
        if(currentIndex==messageCount)
            currentIndex=0;
        ind2 = currentIndex;
        n2Switcher.setText(namesToShow[currentIndex].getName());
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEvaluationFragmentInteractionListener) {
            mListener = (OnEvaluationFragmentInteractionListener) context;
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
    public interface OnEvaluationFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }

    //nacisniecie pierwszego imienia (ind1)
    public void switchNames(View v)
    {
        Log.i("FRAG", "weszlo do switchNames");
        if(v.getId()==n1Switcher.getId())
        {
            //wybrane imie ma tutaj ind1
            int currentScore = 0;
            try {
                currentScore = dbh.getNamesScore(rankingID, namesToShow[ind1].getID());
                dbh.changeNamesScore(rankingID, namesToShow[ind1].getID(), currentScore, addedScore);
            }
            catch (Exception e){
                Toast.makeText(getActivity(), "ranking: " + Integer.toString(rankingID) + " imie: " + Integer.toString(namesToShow[ind1].getID()), Toast.LENGTH_LONG).show();
            }
        }
        else if (v.getId() == n2Switcher.getId())
        {
            //wybrane imie ma tutaj ind2
            int currentScore = 0;
            try{
                currentScore = dbh.getNamesScore(rankingID, namesToShow[ind2].getID());
                dbh.changeNamesScore(rankingID,namesToShow[ind2].getID(), currentScore, addedScore);
            }
            catch(Exception e){
                Toast.makeText(getActivity(), "ranking: " + Integer.toString(rankingID) + " imie: " + Integer.toString(namesToShow[ind2].getID()), Toast.LENGTH_LONG).show();
            }
        }

        ChooseDataForSwitchers();

    }
}
