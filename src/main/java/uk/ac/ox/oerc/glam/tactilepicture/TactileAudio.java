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

    private String curFile;

    private long cmdTime;

    public int duration;

    private MediaPlayer mediaPlayer;

    private Tone tone;

    private  TactileDAO tactileDAO;

    private PlayerState state;

    private int layer = 1;

    protected TactileAudio() {
        this.duration = this.setDuration();
        this.mediaPlayer = new MediaPlayer();
        this.tone = new Tone();
        this.tactileDAO = new TactileDAO();
        this.curFile = "";
        state.setState(PlayerState.PlayerStates.STOPPED);
        /*this.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                su
            }
        });*/
    }

    /**
     * Method to find the duration of the overview
     * @return int
     */
    private int setDuration() {
        return new TactileMediaPlayer(this.mediaPlayer, state).getMediaDuration("overall.mp3");
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

                if (state.getState() == PlayerState.PlayerStates.STOPPED) {
                    if (audioFile == "overall.mp3") {
                        this.startCommand(audioFile, mediaPlayer);
                    } else {
                        this.playCommand(audioFile, this.curFile, mediaPlayer);
                    }
                    state.setState(PlayerState.PlayerStates.PLAYING);
                } else if (state.getState() == PlayerState.PlayerStates.PLAYING) {
                    if (this.curFile.equals(audioFile)) {
                        //Pause if these are the same
                        this.pauseCommand(mediaPlayer);
                        state.setState(PlayerState.PlayerStates.PAUSED);
                    } else {
                        //if this isn't the same file, play the new file
                        if (System.currentTimeMillis() > (cmdTime + this.duration)){
                            this.stopCommand(mediaPlayer);
                            this.playCommand(audioFile, this.curFile, mediaPlayer);
                            this.curFile = audioFile;
                            state.setState(PlayerState.PlayerStates.PLAYING);
                        }
                    }
                } else if (state.getState() == PlayerState.PlayerStates.PAUSED) {
                    if (audioFile.equals(this.curFile)){
                        this.playCommand(audioFile, this.curFile, mediaPlayer);
                        state.setState(PlayerState.PlayerStates.PLAYING);
                    } else {
                        this.stopCommand(mediaPlayer);
                        this.playCommand(audioFile, this.curFile, mediaPlayer);
                        state.setState(PlayerState.PlayerStates.PLAYING);
                    }
                }
           }
        } catch (Exception ex) {
            Log.d("Audio", ex.getMessage());
        }
    }

    private void startCommand(String fName, MediaPlayer mediaPlayer) {
        this.cmdTime = System.currentTimeMillis();
        new TactileMediaPlayer(mediaPlayer, state).execute("play", fName, "");
        state.setState(PlayerState.PlayerStates.PLAYING);
    }

    private void playCommand(String fName, String current, MediaPlayer mediaPlayer) {
        new TactileMediaPlayer(mediaPlayer, state).execute("play", fName, current);
    }

    private void stopCommand(MediaPlayer mediaPlayer) {
        new TactileMediaPlayer(mediaPlayer, state).execute("stop");
        this.resetDuration();
    }

    private void pauseCommand(MediaPlayer mediaPlayer) {
        new TactileMediaPlayer(mediaPlayer, state).execute("pause");
    }


}
