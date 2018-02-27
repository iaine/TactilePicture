package uk.ac.ox.oerc.glam.tactilepicture;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by iain.emsley on 27/02/2018.
 */

public class RecordsAdapter {


    public ArrayAdapter setData(JSONArray jsonArray, ArrayAdapter arrayAdapter) {
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                Log.d("JSON", "Adding record " + jsonArray.get(i));
                arrayAdapter.add(jsonArray.get(i));
            }
        } catch (JSONException jse) {
            Log.d("JSON", "Load JSON object error :" + jse.toString());
        }
        return arrayAdapter;
    }

}
