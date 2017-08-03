package uk.ac.ox.oerc.glam.tactilepicture;

import android.graphics.PointF;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Class to wrap around the Media Player
 */

public class TactileAudio {

    public long stopTime;

    public float x;

    public float y;

    public String aState = "1";

    private String curFile = "";

    private long cmdTime;

    public int duration = 0;

    private MediaPlayer mediaPlayer;

    protected TactileAudio() {
        this.duration = this.setDuration();
        this.mediaPlayer = new MediaPlayer();
    }

    /**
     * Method to find the duration of the overview
     * @return int
     */
    private int setDuration() {
        int dur = 0;
        dur = new TactileMediaPlayer(this.mediaPlayer).getMediaDuration("overview.mp3");
        return dur;
    }

    /**
     * Get the audio from the DAO and handle the player
     *
     * @param event
     * @return
     */
    public void setAudio(PointF event, MediaPlayer mediaPlayer, int duration) {
        /**
         * If x is less 0.10 and y is less that 0.10, state == 3, reset time
         */
        try {
            TactileDAO tactileDAO = new TactileDAO();
            //if stop, the stop all audio action
            if (tactileDAO.getStop(event)) {
                this.stopCommand(mediaPlayer);
            }

            String audioFile = tactileDAO.getAudio(event);

            if (audioFile != "") {
                if (audioFile == "overview.mp3") {
                    this.startCommand(audioFile, mediaPlayer);
                }

                if (audioFile == curFile) {
                    this.pauseCommand(mediaPlayer);
                } else if (audioFile != curFile) {
                    if (System.currentTimeMillis() > (cmdTime + duration)){
                        this.playCommand(audioFile, mediaPlayer);
                    }
                }
            }
        } catch (Exception ex) {
            Log.d("Audio", ex.getMessage());
        }
    }

    private void startCommand(String fName, MediaPlayer mediaPlayer) {
        this.aState = "3";
        this.cmdTime = System.currentTimeMillis();
        new TactileMediaPlayer(mediaPlayer).execute(aState, fName);
    }

    private void playCommand(String fName, MediaPlayer mediaPlayer) {
        new TactileMediaPlayer(mediaPlayer).execute(aState, fName);
    }

    private void stopCommand(MediaPlayer mediaPlayer) {
        new TactileMediaPlayer(mediaPlayer).execute(aState, "");
        this.resetDuration();
    }

    private void resetDuration() {
        duration = 0;
    }

    private void pauseCommand(MediaPlayer mediaPlayer) {
        new TactileMediaPlayer(mediaPlayer).execute("4", "");
        this.aState = "2";
    }

}
