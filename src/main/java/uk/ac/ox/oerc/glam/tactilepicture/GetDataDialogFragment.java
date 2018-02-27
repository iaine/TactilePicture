package uk.ac.ox.oerc.glam.tactilepicture;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import static uk.ac.ox.oerc.glam.tactilepicture.TactileApplication.getAppContext;

/**
 * DialogFragment to load the other pieces of data
 */

public class GetDataDialogFragment extends DialogFragment {

    public static GetDataDialogFragment newInstance() {
        GetDataDialogFragment frag = new GetDataDialogFragment();
        return frag;
    }


    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
        TactileApplication tactileApplication = new TactileApplication();
        AlertDialog.Builder builder = new AlertDialog.Builder(tactileApplication.getAppContext());

        Log.d("File", "Activity discovered");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(tactileApplication.getAppContext(), android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Hardik");
        arrayAdapter.add("Archit");
        arrayAdapter.add("Jignesh");
        arrayAdapter.add("Umang");
        arrayAdapter.add("Gatti");


        /*builder.setTitle("Select a file to load")
                .setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                    }
                });*/

        // Create the AlertDialog object and return it
        //return builder.create();
        //}


        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                Log.d("File", "Got id : " + strName);
                /*AlertDialog.Builder builderInner = new AlertDialog.Builder(getActivity());
                builderInner.setMessage(strName);
                builderInner.setTitle("Your Selected Item is");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();*/
            }
        });
        return builder.create();
    }
}
