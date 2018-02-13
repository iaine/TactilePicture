package uk.ac.ox.oerc.glam.tactilepicture;

import android.graphics.PointF;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Hard coded DAO for application
 */

public class TactileDAO {

    private static int fingerPos = 75;

    /**
     * Method to the audio regarding the
     * @param event
     * @param tactileLayer
     * @param jsonArray
     * @return
     */
    public String getAudio(PointF event, String tactileLayer, JSONArray jsonArray) {

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);
                float x = this.getFloat(c,"x");
                float y = this.getFloat(c,"y");
                if (calculateDistance(event,new PointF(x,y)) < fingerPos){
                    return (tactileLayer == "ONE") ? c.getString("ONE") : c.getString("TWO");
                }
            }
        } catch (JSONException jse) {
            jse.toString();
        }

        return "";
    }

    /**
     * Cast the Double in the JSON Object to a float
     * @param jObj
     * @param c
     * @return
     */
    private float getFloat(JSONObject jObj, String c) {
        return (float)jObj.optDouble(c);
    }

    /**
     * Method to get the second layer of audio where the first is known
     *
     * @param audioName
     * @param jsonArray
     * @return
     */
    public String getAudioByName(String audioName, JSONArray jsonArray) {

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);
                float x = this.getFloat(c,"x");
                float y = this.getFloat(c,"y");
                if (c.getString("ONE").equals(audioName)) {
                    return c.getString("TWO");
                }

            }
        } catch (JSONException jse) {
            jse.toString();
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
