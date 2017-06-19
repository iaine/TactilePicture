package uk.ac.ox.oerc.glam.tactilepicture;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DataConnection extends AsyncTask<String,String,String>  {
    private Context mContext;

    public DataConnection () {
        mContext = TactileApplication.getAppContext();
        Log.d("Context", mContext.toString());
    }

    protected String doInBackground(String... params) {
        this.sendData(params[0]);
        return null;
    }
    public void sendData(String params) {
        try {
            URL url = new URL("http://moggerypi6.oerc.ox.ac.uk/turner_image");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                int length = params.length();
                urlConnection.setFixedLengthStreamingMode(length);

                OutputStream os = urlConnection.getOutputStream();
                Writer writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(params);
                Log.d("connection", params.toString());
                writer.flush();
                writer.close();

            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException me) {
            Log.d("ENCODE",me.getMessage());
        } catch (IOException ioe) {
            Log.d("ENCODE",ioe.getMessage());
        }
    }
}

