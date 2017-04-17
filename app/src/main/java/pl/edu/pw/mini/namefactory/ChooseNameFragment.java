package pl.edu.pw.mini.namefactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Asus on 17.04.2017.
 */

public class ChooseNameFragment extends DialogFragment {

    public interface ChooseNameDialogListener {
        public void onDialogNamePositiveClick(DialogFragment dialog, String name);
        public void onDialogNameNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    ChooseNameDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (ChooseNameDialogListener) activity;
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
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.choose_name, null);
        final EditText nameView = (EditText) dialogView.findViewById(R.id.name);

        builder.setTitle(R.string.choose_name_dialog)
                .setView(dialogView)
                .setPositiveButton(R.string.check, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // ADD NEW ELEMENT
                        try
                        {
                            String name = nameView.getText().toString();

                            if(name.equals(""))
                                throw new Exception();

                            mListener.onDialogNamePositiveClick(ChooseNameFragment.this, name);
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(getContext(), "Wrong data!!" , Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        // Send the negative button event back to the host activity
                        mListener.onDialogNameNegativeClick(ChooseNameFragment.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
