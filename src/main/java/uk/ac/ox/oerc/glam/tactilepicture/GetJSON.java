package uk.ac.ox.oerc.glam.tactilepicture;

import android.content.Context;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import uk.ac.ox.oerc.glam.tactilepicture.AudioDownload;

/**
 * Handler for GETting JSON files
 */

public class GetJSON {

    RequestQueue mRequestQueue;

    private Context mContext;

    private String newUrl;

    public GetJSON () {
        mContext = TactileApplication.getAppContext();
    }

    public void getJSON(String url) {
        // Instantiate the cache
        Cache cache = new DiskBasedCache(mContext.getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the inetwork to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();

        Log.d("File", "URL being called is " + url);
        String[] tmp = url.split("/");
        newUrl =  " http://demeter.oerc.ox.ac.uk/glam/static/tiles/" + tmp[5] + "/audio/";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //write data to file
                        writeFile(response.toString());
                        //get the audio files
                        AudioDownload audioDownload = new AudioDownload();
                        try {
                            JSONArray jsonArray = response.getJSONArray("points");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                audioDownload.downloadAudio(newUrl,jsonObject.getString("ONE"),
                                        jsonObject.getString("TWO"));
                            }
                        } catch (JSONException jse) {
                            Log.d("JSON", "Write json " + jse.toString());
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("File", "File retrieval error: " + error.toString());

                    }
                });
        mRequestQueue.add(jsObjRequest);

    }

    //Method to ge the JSON

    public JSONObject ParseJSON() {
        JSONObject json = null;
        String jsonString = readData();
        try {
            if (jsonString != null) {
                json = new JSONObject(jsonString);
            }
        } catch (Exception e) {
            Log.d("File", "JSON Handling " + e.toString());
        }
        return json;
    }

    private void writeFile (String params) {
        FileOutputStream outputStream;
        String filename = "data.json";
        try {
            File file = new File(mContext.getExternalFilesDir(null), filename);
            // test if file exists.
            if (!file.exists()) {
                file.createNewFile();
            }

            outputStream = new FileOutputStream(file, true);
            outputStream.write(params.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            Log.d("File", "Write exception " + e.toString());
        }
    }

    public String readData() {
        String ret = null;
        try {
            File file = new File(mContext.getExternalFilesDir(null), "data.json");
            if(!file.exists())
            {
                file.createNewFile();
                // write code for saving data to the file
            }
            FileInputStream inputStream = new FileInputStream(file.toString());
            Log.d("File", "File being called is: " + file.toString());

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException fne) {
            Log.d("File" , "File exception error: " + fne.toString());
        } catch (IOException e) {
            Log.d("File" , "Exception error: " + e.toString());
        }
        return ret;
    }
}
