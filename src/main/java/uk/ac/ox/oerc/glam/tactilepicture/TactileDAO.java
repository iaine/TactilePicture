package uk.ac.ox.oerc.glam.tactilepicture;

import android.graphics.PointF;
import android.util.Log;

/**
 * Hard coded DAO for application
 */

public class TactileDAO {

    private static int fingerPos = 75;

    public String getAudio(PointF event, String tactileLayer) {
        if (calculateDistance(event, new PointF(46, 448)) < fingerPos) {
            return (tactileLayer == "ONE") ? "statuette1.mp3" : "statuette2.mp3";
        } else if (calculateDistance(event, new PointF(535, 446)) < fingerPos) {
            return (tactileLayer == "ONE") ? "head1.mp3" : "head2.mp3";
        } else if (calculateDistance(event, new PointF(1270, 575)) < fingerPos) {
            return (tactileLayer == "ONE") ? "window1.mp3" : "window2.mp3";
        } else if (calculateDistance(event, new PointF(630, 818)) < fingerPos) {
            return (tactileLayer == "ONE") ? "torso1.mp3" : "torso2.mp3";
        } else if (calculateDistance(event, new PointF(50, 1307)) < fingerPos) {
            return (tactileLayer == "ONE") ? "table1.mp3" : "table2.mp3";
        } else if (calculateDistance(event, new PointF(301,1755)) < fingerPos) {
            return (tactileLayer == "ONE") ? "cloth1.mp3" : "cloth2.mp3";
        } else if (calculateDistance(event, new PointF(1444, 1235)) < fingerPos) {
            return (tactileLayer == "ONE") ? "medal1.mp3" : "medal2.mp3";
        } else if (calculateDistance(event, new PointF(54, 82)) < fingerPos) {
            return (tactileLayer == "ONE") ? "overview.mp3" : "overview2.mp3";
        }

        return "";
    }

    public String getAudioByName(String audioName) {
        if (audioName == "statuette1.mp3") {
            return "statuette2.mp3";
        } else if (audioName == "head1.mp3") {
            return "head2.mp3";
        } else if (audioName == "window1.mp3") {
            return "window2.mp3";
        } else if (audioName == "torso1.mp3") {
            return "torso2.mp3";
        } else if (audioName == "table1.mp3") {
            return "table2.mp3";
        } else if (audioName == "cloth1.mp3") {
            return "cloth2.mp3";
        } else if (audioName == "medal1.mp3") {
            return "medal2.mp3";
        } else if (audioName == "overview.mp3") {
            return "overview2.mp3";
        }

        return "";
    }

    /**
     * Method that tests for closeness to stop
     * @param e
     * @return
     */
    public boolean getStop (PointF e) {
        if (calculateDistance(e, new PointF(54, 187)) < fingerPos) {
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
