package uk.ac.ox.oerc.glam.tactilepicture;

import android.graphics.PointF;
import android.util.Log;

/**
 * Hard coded DAO for application
 */

public class TactileDAO {

    private static int fingerPos = 75;

    public String getAudio(PointF event, String tactileLayer) {
        if (calculateDistance(event, new PointF(1495, 1898)) < fingerPos) {
            return (tactileLayer == "ONE") ? "statuette1.wav" : "statuette2.wav";
        } else if (calculateDistance(event, new PointF(1011, 1893)) < fingerPos) {
            return (tactileLayer == "ONE") ? "head1.wav" : "head2.wav";
        } else if (calculateDistance(event, new PointF(285, 1760)) < fingerPos) {
            return (tactileLayer == "ONE") ? "window1.wav" : "window2.mp3";
        } else if (calculateDistance(event, new PointF(943, 1526)) < fingerPos) {
            return (tactileLayer == "ONE") ? "torso1.wav" : "torso2.mp3";
        } else if (calculateDistance(event, new PointF(1533, 1027)) < fingerPos) {
            return (tactileLayer == "ONE") ? "table1.wav" : "table2.mp3";
        } else if (calculateDistance(event, new PointF(1270, 620)) < fingerPos) {
            return (tactileLayer == "ONE") ? "cloth1.wav" : "cloth2.mp3";
        } else if (calculateDistance(event, new PointF(120, 1150)) < fingerPos) {
            return (tactileLayer == "ONE") ? "medal1.mp3" : "medal2.mp3";
        } else if (calculateDistance(event, new PointF(1520, 2280)) < fingerPos) {
            return (tactileLayer == "ONE") ? "overview.mp3" : "overview2.wav";
        }

        return "";
    }

    public String getAudioByName(String audioName) {
        if (audioName == "statuette1.wav") {
            return "statuette2.wav";
        } else if (audioName == "head1.wav") {
            return "head2.wav";
        } else if (audioName == "window1.wav") {
            return "window2.wav";
        } else if (audioName == "torso1.wav") {
            return "torso2.mp3";
        } else if (audioName == "table1.wav") {
            return "table2.mp3";
        } else if (audioName == "cloth1.wav") {
            return "cloth2.mp3";
        } else if (audioName == "medal1.mp3") {
            return "medal2.mp3";
        } else if (audioName == "overview.mp3") {
            return "overview2.wav";
        }

        return "";
    }

    /**
     * Method that tests for closeness to stop
     * @param e
     * @return
     */
    public boolean getStop (PointF e) {
        if (calculateDistance(e, new PointF(1520, 2180)) < fingerPos) {
            Log.d("Position", "Returning true");
            return true;
        }
        return false;
    }

    /**
     * Function to calculate the distance between the MotionEvent and
     * the stored point.
     *
     * @param p1 Point
     * @param p2 Point
     * @return double
     */
    private double calculateDistance (PointF p1, PointF p2) {
        return (double)Math.sqrt((double)Math.pow((p1.x - p2.x), 2.0)
                + (double)Math.pow((p1.y - p2.y), 2.0));
    }
}
