package uk.ac.ox.oerc.glam.tactilepicture;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Build;
import android.util.Log;

/**
 * Class to handle tone creation
 */

public class Tone {
    private double[] mSound;

    private short[] mBuffer;

    private int toneduration = 44100;

    //protected AudioTrack mAudioTrack;

    protected Tone() {
        this.mSound = new double[4410];
        this.mBuffer = new short[toneduration];
        // AudioTrack definition
        this.createTone(440, toneduration);

    }

    /**
     * Method to create the tone and buffer on initialisation
     * @param frequency
     * @param duration
     */
    private void createTone(double frequency, int duration) {
        // Sine wave
        for (int i = 0; i < this.mSound.length; i++) {
            this.mSound[i] = Math.sin((2.0*Math.PI * i/(44100/frequency)));
            this.mBuffer[i] = (short) (this.mSound[i]*Short.MAX_VALUE);
        }
    }

    /**
     * Method to play the tone when the audio button is pressed
     */
    public void playTone() {
        int mBufferSize = AudioTrack.getMinBufferSize(44100,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_8BIT);
        Log.d("Tone", Integer.toString(mBufferSize));
        AudioTrack mAudioTrack =  new AudioTrack(AudioManager.STREAM_MUSIC, 44100,
                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT,
                mBufferSize, AudioTrack.MODE_STREAM);


        mAudioTrack.play();

        mAudioTrack.write(this.mBuffer, 0, this.mSound.length);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mAudioTrack.setVolume(AudioTrack.getMaxVolume());
        }
        mAudioTrack.stop();
        mAudioTrack.release();
    }


}
