package pl.edu.pw.mini.namefactory;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

import java.util.Random;

import pl.edu.pw.mini.namefactory.DatabasePackage.DatabaseHandler;
import pl.edu.pw.mini.namefactory.Names.Name;


public class EvaluationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ID = "rankingID";

    private TextSwitcher n1Switcher;
    private TextSwitcher n2Switcher;
    private String rankingName;
    private int rankingID;
    private DatabaseHandler dbh;
    private boolean isGirl;

    // Array of String to Show In TextSwitcher
    private pl.edu.pw.mini.namefactory.Names.Name[] namesToShow;
    int messageCount;
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

        this.dbh = RankingsListMain.dbh;
        if (getArguments() != null) {

            rankingID = getArguments().getInt(ARG_ID);
            rankingName = dbh.getRankingName(rankingID);
            namesToShow = dbh.getNameList(rankingID).toArray(new Name[0]);
            if(namesToShow[0].getIsGirl())
                isGirl = true;
            else
                isGirl= false;
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

        SetupView(view);

        return view;
    }

    //@TargetApi(16)
    void SetupView(View _view)
    {
        mListener.hideFloatingButton();
        mListener.setTitleName(rankingName);


        n1Switcher = (TextSwitcher) _view.findViewById(R.id.name1);
        n2Switcher = (TextSwitcher) _view.findViewById(R.id.name2);
        if(isGirl)
        {
            n1Switcher.setBackgroundColor(getResources().getColor(R.color.colorGirl));
            //n1Switcher.setBackground(getResources().getDrawable(R.drawable.tlo_girl));
            n2Switcher.setBackgroundColor(getResources().getColor(R.color.colorGirlDark));
        }
        else
        {
            n1Switcher.setBackgroundColor(getResources().getColor(R.color.colorBoy));
            //n1Switcher.setBackground(getResources().getDrawable(R.drawable.tlo_boy));
            n2Switcher.setBackgroundColor(getResources().getColor(R.color.colorBoyDark));
        }


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

        n1Switcher.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switchNames(n1Switcher);
            }
        });
        n2Switcher.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switchNames(n2Switcher);
            }
        });
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        populateViewForOrientation(inflater, (ViewGroup) getView());
    }

    private void populateViewForOrientation(LayoutInflater inflater, ViewGroup viewGroup) {
        viewGroup.removeAllViewsInLayout();
        View subview = inflater.inflate(R.layout.fragment_evaluation, viewGroup);

        subview.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
        subview.setClickable(true);

        SetupView(subview);
    }

    private void ChooseDataForSwitchers()
    {
        Random random = new Random();
        ind1 = random.nextInt(messageCount);
        n1Switcher.setText(namesToShow[ind1].getName());

        do {
            ind2 = random.nextInt(messageCount);
        } while(ind1 == ind2);
        n2Switcher.setText(namesToShow[ind2].getName());
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
        //void onFragmentInteraction();
        void hideFloatingButton();
        void setTitleName(String name);
    }

    //nacisniecie pierwszego imienia (ind1)
    public void switchNames(View v)
    {
        if(v.getId()==n1Switcher.getId())
        {
            //wybrane imie ma tutaj ind1
            Runnable newEvoTask = new Runnable() {
                @Override
                public void run() {
                    double currentWinnerScore = 0, currentLoserScore = 0;
                    try {
                        currentWinnerScore = dbh.getNamesScore(rankingID, namesToShow[ind1].getID());
                        currentLoserScore = dbh.getNamesScore(rankingID, namesToShow[ind2].getID());

                        EloUpdatedPair newScores = Elo.getUpdatedScore(currentWinnerScore, currentLoserScore);

                        dbh.changeNamesScore(rankingID, namesToShow[ind1].getID(), newScores.getWinnerPoints());
                        dbh.changeNamesScore(rankingID, namesToShow[ind2].getID(), newScores.getLoserPoints());

                        RankingsListMain.apiWrapper.createNewMatch(rankingID, namesToShow[ind1].getID(), namesToShow[ind2].getID());
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "ranking: " + Integer.toString(rankingID) + " imie: " + Integer.toString(namesToShow[ind1].getID()), Toast.LENGTH_LONG).show();
                    }
                }
            };
            RankingsListMain.fixedPool.submit(newEvoTask);

        }
        else if (v.getId() == n2Switcher.getId())
        {
            //wybrane imie ma tutaj ind2
            Runnable newEvoTask = new Runnable() {
                @Override
                public void run() {
                    double currentWinnerScore = 0, currentLoserScore = 0;
                    try {
                        currentWinnerScore = dbh.getNamesScore(rankingID, namesToShow[ind2].getID());
                        currentLoserScore = dbh.getNamesScore(rankingID, namesToShow[ind1].getID());

                        EloUpdatedPair newScores = Elo.getUpdatedScore(currentWinnerScore, currentLoserScore);

                        dbh.changeNamesScore(rankingID, namesToShow[ind2].getID(), newScores.getWinnerPoints());
                        dbh.changeNamesScore(rankingID, namesToShow[ind1].getID(), newScores.getLoserPoints());

                        RankingsListMain.apiWrapper.createNewMatch(rankingID, namesToShow[ind2].getID(), namesToShow[ind1].getID());
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "ranking: " + Integer.toString(rankingID) + " imie: " + Integer.toString(namesToShow[ind2].getID()), Toast.LENGTH_LONG).show();
                    }
                }
            };
            RankingsListMain.fixedPool.submit(newEvoTask);
        }

        ChooseDataForSwitchers();

    }
}
