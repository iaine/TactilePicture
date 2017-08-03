package uk.ac.ox.oerc.glam.tactilepicture;

import android.graphics.PointF;
import android.view.MotionEvent;

/**
 * Hard coded DAO for application
 */

public class TactileDAO {

    private static int fingerPos = 75;

    public String getAudio(PointF event) {
        if (calculateDistance(event, new PointF(170, 860)) < fingerPos) {
            return "overview.mp3";
        } else if (calculateDistance(event, new PointF(750, 1090)) < fingerPos) {
            return "ladder.mp3";
        } else if (calculateDistance(event, new PointF(980, 960)) < fingerPos) {
            return "carfax.mp3";
        } else if (calculateDistance(event, new PointF(1090, 780)) < fingerPos) {
            return "stmarys.wav";
        } else if (calculateDistance(event, new PointF(1880, 320)) < fingerPos) {
            return "allsaints.mp3";
        } else if (calculateDistance(event, new PointF(1513, 1342)) < fingerPos) {
            return "scholars.mp3";
        } else if (calculateDistance(event, new PointF(915, 1354)) < fingerPos) {
            return "highstreet.mp3";
        } else if (calculateDistance(event, new PointF(75, 75)) < fingerPos) {
            return "overview.mp3";
        }

        return "";
    }

    /**
     * Method that tests for closeness to stop
     * @param e
     * @return
     */
    public boolean getStop (PointF e) {
        if (calculateDistance(e, new PointF(75, 225)) < fingerPos) {
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
