package uk.ac.ox.oerc.glam.tactilepicture;

import android.graphics.PointF;
import android.media.MediaPlayer;
import android.util.Log;

import  uk.ac.ox.oerc.glam.tactilepicture.Tone;

/**
 * Class to wrap around the Media Player
 */

public class TactileAudio {

    private String curFile;

    private long cmdTime;

    public int duration;

    private MediaPlayer mediaPlayer;

    private Tone tone;

    private  TactileDAO tactileDAO;

    private PlayerState state = new PlayerState();

    private TactileLayer tl = new TactileLayer();

    private String layer = "";

    private int curPos = 0;

    private String audioFile;

    protected TactileAudio() {
        this.duration = this.setDuration();
        this.mediaPlayer = new MediaPlayer();
        this.tone = new Tone();
        this.tactileDAO = new TactileDAO();
        this.curFile = "";
        state.setState(PlayerState.PlayerStates.STOPPED);
        tl.setTactileLayers(TactileLayer.TactileLayers.ONE);
        layer = tl.getTactileLayers().name();
        this.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (tl.getTactileLayers() == TactileLayer.TactileLayers.ONE) {
                    /*String newLevelAudio = tactileDAO.getAudioByName(audioFile);
                    curPos = 0;
                    curFile = newLevelAudio;*/
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    tl.setTactileLayers(TactileLayer.TactileLayers.TWO);
                    state.setState(PlayerState.PlayerStates.STOPPED);
                    /*new TactileMediaPlayer(mediaPlayer, state).execute("play",
                            newLevelAudio, "0");*/

                } else {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    tl.setTactileLayers(TactileLayer.TactileLayers.ONE);
                    /*curPos = 0; */
                    //new TactileMediaPlayer(mediaPlayer, state).execute("stop");
                    state.setState(PlayerState.PlayerStates.STOPPED);
                }
            }
        });
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
     * @param event PointF from the screen
     */
    public boolean setAudio(PointF event) {
        boolean aState = false;
        try {

            //if stop, the stop all audio action
            if (this.tactileDAO.getStop(event)) {
                this.stopCommand(mediaPlayer);
                curPos = 0;
                this.curFile = "";
                layer = tl.getTactileLayers().name();
                audioFile = this.tactileDAO.getAudio(event, layer);
                state.setState(PlayerState.PlayerStates.STOPPED);
            }

            layer = tl.getTactileLayers().name();
            audioFile = this.tactileDAO.getAudio(event, layer);

            if (audioFile != "") {
                //play the tone first as an alert
                tone.playTone();

                if (state.getState() == PlayerState.PlayerStates.STOPPED) {

                    if (audioFile == "overall.mp3") {
                        this.startCommand(audioFile, mediaPlayer);
                    } else {
                        curPos = 0;
                        Log.d("Play", "inbound " + curFile + " audio " + audioFile + " layer " + layer);
                        // if the file has swapped, then move back to layer 1
                        if (curFile != audioFile && !curFile.isEmpty()) {
                            Log.d("Play", "current " + curFile + " audio " + audioFile);
                            //if (curFile.substring(0,4) != audioFile.substring(0, 4)) {
                                tl.setTactileLayers(TactileLayer.TactileLayers.ONE);
                                layer = tl.getTactileLayers().name();
                                audioFile = this.tactileDAO.getAudio(event, layer);
                            //}
                        } else if (curFile == audioFile && !curFile.isEmpty()) {
                            if (layer == "ONE") {
                                tl.setTactileLayers(TactileLayer.TactileLayers.TWO);
                            }
                        }

                        //layer = tl.getTactileLayers().name();
                        //audioFile = this.tactileDAO.getAudio(event, layer);
                        Log.d("Play", "current2 " + curFile + " audio file " + audioFile);
                        this.playCommand(audioFile, this.getPos(curPos),mediaPlayer);
                        this.curFile = audioFile;
                    }
                    state.setState(PlayerState.PlayerStates.PLAYING);

                } else if (state.getState() == PlayerState.PlayerStates.PLAYING) {
                    if (this.curFile.equals(audioFile)) {
                        //if layer is one, switch to two and play
                        if (tl.getTactileLayers() == TactileLayer.TactileLayers.ONE) {
                            tl.setTactileLayers(TactileLayer.TactileLayers.TWO);
                            layer = tl.getTactileLayers().name();
                            audioFile = this.tactileDAO.getAudio(event, layer);
                            this.pauseCommand(mediaPlayer);
                            curPos = 0;
                            this.playCommand(audioFile, this.getPos(curPos),mediaPlayer);
                            curFile = audioFile;
                        } else {

                            //Pause if these are the same
                            curPos = 0;
                            //if layer is two, pause.
                            this.pauseCommand(mediaPlayer);
                            curFile = audioFile;
                            tl.setTactileLayers(TactileLayer.TactileLayers.ONE);
                            state.setState(PlayerState.PlayerStates.STOPPED);
                        }
                    } else {
                        //if this isn't the same file, play the new file
                            this.stopCommand(mediaPlayer);
                            Log.d("Play", "Layer" + layer + "New file");
                            curPos = 0;
                            tl.setTactileLayers(TactileLayer.TactileLayers.ONE);
                            layer = tl.getTactileLayers().name();
                            audioFile = this.tactileDAO.getAudio(event, layer);
                            this.playCommand(audioFile, this.getPos(curPos), mediaPlayer);
                            this.curFile = audioFile;
                            state.setState(PlayerState.PlayerStates.PLAYING);
                    }
                } else if (state.getState() == PlayerState.PlayerStates.PAUSED) {
                    if (audioFile.equals(this.curFile)){
                        this.playCommand(audioFile, this.getPos(curPos), mediaPlayer);
                        curPos = 0;
                        state.setState(PlayerState.PlayerStates.PLAYING);
                    } else {
                        this.stopCommand(mediaPlayer);
                        curPos = 0;
                        tl.setTactileLayers(TactileLayer.TactileLayers.ONE);
                        layer = tl.getTactileLayers().name();
                        audioFile = this.tactileDAO.getAudio(event, layer);
                        this.playCommand(audioFile, this.getPos(curPos), mediaPlayer);
                        this.curFile = audioFile;
                        //set states. Assume if pause, we've cycled through states
                        tl.setTactileLayers(TactileLayer.TactileLayers.ONE);
                        state.setState(PlayerState.PlayerStates.PLAYING);
                    }
                }
                return true;
           }

        } catch (Exception ex) {
            Log.d("Audio", ex.getMessage());
        }
        return false;
    }

    private String getPos (int pos) {
        return Integer.toString(pos);
    }

    private void startCommand(String fName, MediaPlayer mediaPlayer) {
        this.cmdTime = System.currentTimeMillis();
        new TactileMediaPlayer(mediaPlayer, state).execute("play", fName, "0");
        state.setState(PlayerState.PlayerStates.PLAYING);
    }

    private void playCommand(String fName, String pos, MediaPlayer mediaPlayer) {
        new TactileMediaPlayer(mediaPlayer, state).execute("play", fName, pos);
    }

    private void stopCommand(MediaPlayer mediaPlayer) {
        new TactileMediaPlayer(mediaPlayer, state).execute("stop");
        tl.setTactileLayers(TactileLayer.TactileLayers.ONE);
        this.resetDuration();
    }

    private void pauseCommand(MediaPlayer mediaPlayer) {
        new TactileMediaPlayer(mediaPlayer, state).execute("pause");
    }
}
