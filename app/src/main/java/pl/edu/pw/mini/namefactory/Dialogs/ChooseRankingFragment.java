package pl.edu.pw.mini.namefactory.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.ArrayList;

import pl.edu.pw.mini.namefactory.R;

/**
 * Created by Asus on 17.04.2017.
 */

public class ChooseRankingFragment extends DialogFragment {

    public enum RankingDialogType{
        CONNECTION,
        EVALUATION,
        SHOWGLOBAL,
    }

    public interface ChooseRankingDialogListener {
        public void onDialogRankingPositiveClick(DialogFragment dialog, Integer id, RankingDialogType operationType);
        public void onDialogRankingNegativeClick(DialogFragment dialog);
    }

    public void setArguments(Bundle b)
    {
        // get the Bundle that stores the data of this Activity
        ArrayList<String> rNames = (ArrayList<String>) b.get("rankings");
        rIDS = (ArrayList<Integer>) b.get("rankingsID");
        rankingsNames =rNames.toArray(new CharSequence[rNames.size()]);

        operationType = (RankingDialogType) b.get("type");
    }


    CharSequence[] rankingsNames;
    ArrayList<Integer> rIDS;
    RankingDialogType operationType;
    int ranking;

    // Use this instance of the interface to deliver action events
    ChooseRankingDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (ChooseRankingDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement ChooseNameDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
       // LayoutInflater inflater = getActivity().getLayoutInflater();
       // final View dialogView = inflater.inflate(R.layout.choose_name, null);
       // final EditText nameView = (EditText) dialogView.findViewById(R.id.name);

        builder.setTitle(R.string.choose_ranking_dialog)
                .setSingleChoiceItems(rankingsNames,-1, new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ranking = which;
                            }
                        })
                .setPositiveButton(R.string.choose, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // ADD NEW ELEMENT

                            mListener.onDialogRankingPositiveClick(ChooseRankingFragment.this, rIDS.get(ranking), operationType);

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        // Send the negative button event back to the host activity
                        mListener.onDialogRankingNegativeClick(ChooseRankingFragment.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
