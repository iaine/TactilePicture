package uk.ac.ox.oerc.glam.tactilepicture;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by iain.emsley on 22/02/2018.
 */

public class MyAlertDialogFragment extends DialogFragment implements LoaderManager.LoaderCallbacks<JSONArray>  {

    public ArrayAdapter arrayAdapter;

    public static MyAlertDialogFragment newInstance() {
        MyAlertDialogFragment frag = new MyAlertDialogFragment();
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //arrayAdapter = new RecordsAdapter(getActivity());
        arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.select_dialog_singlechoice);

        getLoaderManager().initLoader(0, null, this).forceLoad();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                String strName = (String) arrayAdapter.getItem(which);
                Log.d("File", "Got id : " + strName);
                //once the selection is made, get the data file
                final GetJSON getJSON = new GetJSON();
                getJSON.getJSON("http://demeter.oerc.ox.ac.uk/glam/record/"+ strName +"/json");
            }
        });

        return builder.create();
    }

    @Override
    public Loader<JSONArray> onCreateLoader(int i, Bundle bundle) {
        Log.d("JSON", "Insude create loader");
        return new FetchData(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<JSONArray> loader, JSONArray jsonArray) {
         arrayAdapter = new RecordsAdapter().setData(jsonArray, arrayAdapter);
         Log.d("JSON", "ArrayAdapter :" + arrayAdapter.toString());
    }

    @Override
    public void onLoaderReset(Loader<JSONArray> loader) {

    }


    public static class FetchData extends AsyncTaskLoader<JSONArray> {

        public FetchData(Context context) {
            super(context);
        }

        @Override
        public JSONArray loadInBackground() {
            Log.d("JSON", "Inside URL call for " );
            JSONObject obj = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            StringBuilder sb = new StringBuilder();
            try {

                URL url = new URL("http://demeter.oerc.ox.ac.uk/glam/json");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.connect();
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                conn.disconnect();
                obj  = new JSONObject(sb.toString());
                jsonArray = obj.getJSONArray("records");
                Log.d("JSON", "JArray " + jsonArray.toString());
            } catch (Exception e) {
                Log.d("JSON", "File " + e.toString());
            }
            return jsonArray;
        }

        @Override
        public void deliverResult(JSONArray data) {

            super.deliverResult(data);
        }
    }// end inner class
}
