package uk.ac.ox.oerc.glam.tactilepicture;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;


/**
 * Handler for GETting JSON files
 */

public class AudioDownload {

    private Context mContext;

    private DownloadManager dm;

    private long enqueue;

    public AudioDownload() {
        mContext = TactileApplication.getAppContext();
        dm = (DownloadManager) mContext.getSystemService(mContext.DOWNLOAD_SERVICE);
    }

    protected String downloadAudio (String url, String fileNameOne, String fileNameTwo) {
        try {

            DownloadManager.Request request = new Request(Uri.parse((url + fileNameOne).trim()));
            request.setDestinationUri(Uri.fromFile(this.writeFileName(fileNameOne)));
            dm.enqueue(request);


            request = new Request(Uri.parse((url + fileNameTwo).trim()));
            request.setDestinationUri(Uri.fromFile(this.writeFileName(fileNameTwo)));
            enqueue = dm.enqueue(request);

            BroadcastReceiver receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();

                    if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                        Log.d("File", "File download successful");
                    } else {
                        Log.d("File", "There was an error");
                    }
                }
            };


        } catch (Exception e) {
            Log.d("File", "Download Exception " + e.toString());
        }
        return null;
    }

    /**
     * Method to create the file name in  the music directory
     */
    private File writeFileName (String fname) {
        File file = null;
        try {
            String PATH_TO_FILE = "/mus_audio/"+fname;
            File path = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_MUSIC);
            file = new File(path, PATH_TO_FILE);
        } catch (Exception e) {
            Log.d("File", "AudioFile writing: " + e.toString());
        }
        return file;
    }


}