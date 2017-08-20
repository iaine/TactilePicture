package uk.ac.ox.oerc.glam.tactilepicture;

import android.graphics.PointF;
import android.media.MediaPlayer;
import android.util.Log;

import  uk.ac.ox.oerc.glam.tactilepicture.Tone;

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

    public int duration;

    private MediaPlayer mediaPlayer;

    private Tone tone;

    private  TactileDAO tactileDAO;

    protected TactileAudio() {
        this.duration = this.setDuration();
        this.mediaPlayer = new MediaPlayer();
        this.tone = new Tone();
        this.tactileDAO = new TactileDAO();
    }

    /**
     * Method to find the duration of the overview
     * @return int
     */
    private int setDuration() {
        return new TactileMediaPlayer(this.mediaPlayer).getMediaDuration("overall.mp3");
    }

    /**
     * Method to reset the duration to 0
     */
    private void resetDuration() {
        this.duration = 0;
    }

    /**
     * Get the audio from the DAO and handle the player
     *
     * @param event
     * @return
     */
    public void setAudio(PointF event) {

        try {

            //if stop, the stop all audio action
            if (this.tactileDAO.getStop(event)) {
                this.stopCommand(mediaPlayer);
            }

            String audioFile = this.tactileDAO.getAudio(event);

            if (audioFile != "") {
                //play the tone first as an alert
                tone.playTone();
                if (audioFile == "overall.mp3") {
                    this.startCommand(audioFile, mediaPlayer);
                }

                if (audioFile == curFile) {
                    this.pauseCommand(mediaPlayer);
                } else if (audioFile != curFile) {
                    if (System.currentTimeMillis() > (cmdTime + this.duration)){
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

    private void pauseCommand(MediaPlayer mediaPlayer) {
        new TactileMediaPlayer(mediaPlayer).execute("4", "");
        this.aState = "2";
    }


}
