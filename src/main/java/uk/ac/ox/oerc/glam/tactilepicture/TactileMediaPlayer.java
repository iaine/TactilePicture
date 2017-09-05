package uk.ac.ox.oerc.glam.tactilepicture;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import static android.os.Environment.DIRECTORY_MUSIC;
import static android.os.Environment.getExternalStorageState;

/**
 * MediaPlayer functions.
 */

public class TactileMediaPlayer extends AsyncTask<String,String,String> {

    private Context mContext;

    public MediaPlayer mediaPlayer;

    public PlayerState pstate;

    public TactileMediaPlayer (MediaPlayer mPlayer, PlayerState state) {
        mContext = TactileApplication.getAppContext();
        this.mediaPlayer = mPlayer;
        this.pstate = state;
    }


    public int getMediaDuration (String fileName) {
        int duration = -2;
        String PATH_TO_FILE = "/mus_audio/"+fileName;
        File path = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_MUSIC);
        File file = new File(path, PATH_TO_FILE);
        try {
            MediaPlayer mp = new MediaPlayer();
            if (mp != null) {
                mp.setDataSource(file.toString());
                mp.prepare();
                duration = mp.getDuration();
                mp.reset();
                mp.release();
            }
        } catch (Exception e) {
            Log.d("Media", e.getStackTrace().toString());
        }
        return duration;
    }


    public void PlayMedia(String fileName, String Pos) {
        Log.d("Play", "File " + fileName + " position " + Pos);
        int seekPos  = (Pos !=  "0" || Pos == "") ? Integer.parseInt(Pos): 0;

        //if the file isn't the same stop it.
        String PATH_TO_FILE = "/mus_audio/"+fileName;
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MUSIC);
        File file = new File(path, PATH_TO_FILE);

        try {

            this.mediaPlayer.setDataSource(file.toString());
            this.mediaPlayer.prepare();
            if (seekPos > 0) {
                this.mediaPlayer.seekTo(seekPos);
            }
            this.mediaPlayer.start();
            this.mediaPlayer.setVolume((float)0.8, (float)0.8);

        } catch (Exception e) {
            Log.d("Media", e.getStackTrace().toString());
        }
    }

    public void StopMedia() {
        Log.d("Play", "Stopping state");
        this.mediaPlayer.stop();
        this.mediaPlayer.reset();
        //this.mediaPlayer.release();
    }

    public void PauseMedia() {
        Log.d("Play", "Pause state in MP");
        this.mediaPlayer.pause();
        this.mediaPlayer.reset();
    }

    public int getCurrentPosition() {
        return this.mediaPlayer.getCurrentPosition();
    }

    /**
     * For now, only handle stop/start
     */
    protected String doInBackground(String... params) {
        switch (params[0]) {
            case "play":
                this.PlayMedia(params[1], params[2]);
                break;
            case "stop":
                this.StopMedia();
                break;
            case "pause":
                this.PauseMedia();
                break;
        }
        return null;
    }
}
