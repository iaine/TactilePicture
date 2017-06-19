package uk.ac.ox.oerc.glam.tactilepicture;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.IntegerRes;
import android.util.Log;

import java.io.File;

import static android.os.Environment.DIRECTORY_MUSIC;

/**
 * Created by iain.emsley on 11/06/2017.
 */

public class TactileMediaPlayer extends AsyncTask<String,String,String> {

    private Context mContext;

    public MediaPlayer mediaPlayer = new MediaPlayer();

    public TactileMediaPlayer () {
        mContext = TactileApplication.getAppContext();
        //Log.d("Context", mContext.toString());
    }


    public void PlayMedia( String fileName) {
        String PATH_TO_FILE = "/mus_audio/"+fileName;
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MUSIC);
        File file = new File(path, PATH_TO_FILE);
        Log.d ("Play", "Filepath: " + file.toString());
        try {
            mediaPlayer.setDataSource(file.toString());
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setVolume((float)0.8, (float)0.8);
        } catch (Exception e) {
            Log.d("Media", e.getMessage());
        }
    }

    public void StopMedia() {
        mediaPlayer.stop();
    }

    @Override
    /**
     * For now, only handle stop/start
     */
    protected String doInBackground(String... params) {
        Log.d("Background", "State " + params[0] + " File " + params[1] );
        switch (params[0]) {
            case "1":
                this.PlayMedia(params[1]);
                break;
            case "2":
                this.StopMedia();
                break;
        }
        return null;
    }
}
