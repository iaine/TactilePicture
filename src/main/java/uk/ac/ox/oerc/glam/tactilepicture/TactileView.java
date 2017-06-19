package uk.ac.ox.oerc.glam.tactilepicture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Handles the touch events.
 */

public class TactileView extends View {

    public String aState = "1";

    public long cmdTime;

    public float x;

    public float y;

    private SparseArray<PointF> mActivePointers;
    private Paint mPaint;

    public TactileView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d("View", "in view");

        initView();
    }

    private void initView() {
        mActivePointers = new SparseArray<PointF>();
        mActivePointers.put(1,new PointF(170, 860));
        mActivePointers.put(2,new PointF(750, 1070));
        mActivePointers.put(3,new PointF(985, 970));
        mActivePointers.put(4,new PointF(1090, 780));
        mActivePointers.put(5,new PointF(1870, 310));
        mActivePointers.put(6,new PointF(1560, 1340));

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.CYAN);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = getXPosition(event);
        y = getYPosition(event);

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

            case MotionEvent.ACTION_POINTER_UP:
            //case MotionEvent.ACTION_HOVER_EXIT: // Hide for now, might be too fast
                this.onPress(event);
                break;
            default:
                return false;
        }
        return true;
    }

        /**
         * Fires on up motion
         *
         * @param e
         * @return
         */
    public boolean onPress(MotionEvent e) {
            /**
             * If x is less 0.10 and y is less that 0.10, state == 3, reset time
             */
            Log.d("Position", "x: " + this.getXPosition(e) + "y: " + this.getYPosition(e));
            if (this.getXPosition(e) < 0.10) {
                //overview audio
                if (this.getYPosition(e) < 0.10) {
                    if (System.currentTimeMillis() > (cmdTime + (2 * 60 * 1000))) {
                        this.startCommand("command.mp3");
                    } else {
                        this.stopCommand();
                    }
                } else if (this.getYPosition(e) < 0.20) {
                    //stop command
                    this.stopCommand();
                }
            } else {
                this.getAudio(e);
            }
            return true;
        }

        private void getAudio(MotionEvent e) {
            try {
                /*Float x = this.getXPosition(e);
                Float y = this.getYPosition(e);*/
                PointF event = new PointF(this.getXPosition(e), this.getYPosition(e));
                Log.d("getAudio", "State is " + this.aState);
                if (calculateDistance(event, new PointF(170, 860)) < 120) {
                    if (this.aState == "1") {

                        this.playCommand("windows.wav");
                        this.aState = "2";
                    } else if (aState == "2"){
                        this.stopCommand();
                    } else if (aState == "3" &&
                            System.currentTimeMillis() > (cmdTime + (2 * 60 * 1000))) {
                        this.playCommand("windows.wav");
                        this.aState = "1";
                    }
                } else if (calculateDistance(event, new PointF(750, 1090)) < 120) {
                    if (this.aState == "1") {

                        this.playCommand("ladder.wav");
                        this.aState = "2";
                    } else if (aState == "2"){
                        this.stopCommand();
                    } else if (aState == "3" &&
                            System.currentTimeMillis() > (cmdTime + (2 * 60 * 1000))) {
                        this.playCommand("ladder.wav");
                        this.aState = "1";
                    }
                } else if (calculateDistance(event, new PointF(980, 960)) < 120) {
                    if (this.aState == "1") {

                        this.playCommand("carfax.wav");
                        this.aState = "2";
                    } else if (aState == "2"){
                        this.stopCommand();
                    } else if (aState == "3" &&
                            System.currentTimeMillis() > (cmdTime + (2 * 60 * 1000))) {
                        this.playCommand("carfax.wav");
                        this.aState = "1";
                    }
                } else if (calculateDistance(event, new PointF(1090, 780)) < 120) {
                    if (this.aState == "1") {

                        this.playCommand("stmarys.wav");
                        this.aState = "2";
                    } else if (aState == "2"){
                        this.stopCommand();
                    } else if (aState == "3" &&
                            System.currentTimeMillis() > (cmdTime + (2 * 60 * 1000))) {
                        this.playCommand("stmarys.wav");
                        this.aState = "1";
                    }
                }   else if (calculateDistance(event, new PointF(1880, 320)) < 120) {
                    if (this.aState == "1") {

                        this.playCommand("allsaintsspire.wav");
                        this.aState = "2";
                    } else if (aState == "2"){
                        this.stopCommand();
                    } else if (aState == "3" &&
                            System.currentTimeMillis() > (cmdTime + (2 * 60 * 1000))) {
                        this.playCommand("allsaintsspire.wav");
                        this.aState = "1";
                    }
                } else if (calculateDistance(event, new PointF(1550, 1340)) < 120) {
                    if (this.aState == "1") {

                        this.playCommand("staff.wav");
                        this.aState = "2";
                    } else if (aState == "2"){
                        this.stopCommand();
                    } else if (aState == "3" &&
                            System.currentTimeMillis() > (cmdTime + (2 * 60 * 1000))) {
                        this.playCommand("staff.wav");
                        this.aState = "1";
                    }
                }
            } catch (Exception ex) {
                Log.d("Audio", ex.getMessage());
            }
        }

    /**
     * Function to calculate the distance between the MotionEvent and
     * the stored point.
     *
     * @param p1
     * @param p2
     * @return
     */
        private double calculateDistance (PointF p1, PointF p2) {
            return (double)Math.sqrt((double)Math.pow((p1.x - p2.x), 2.0)
                                   + (double)Math.pow((p1.y - p2.y), 2.0));
        }

        private void startCommand(String fName) {
            this.aState = "3";
            this.cmdTime = System.currentTimeMillis();
            new TactileMediaPlayer().execute(aState, fName);
        }

        private void playCommand(String fName) {
            new TactileMediaPlayer().execute(aState, fName);
        }

        private void stopCommand() {
            this.aState = "1";
            new TactileMediaPlayer().execute(aState, "");
        }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // draw all pointers
        for (int size = mActivePointers.size(), i = 0; i < size; i++) {
            PointF point = mActivePointers.valueAt(i);
            if (point != null) {
                mPaint.setColor(Color.CYAN);
            }
            canvas.drawCircle(point.x, point.y, 100, mPaint);
        }
    }

    private SparseArray getPoints(SparseArray mActivePointers) {

        try {
            mActivePointers.put(1,new PointF(170, 860));
            mActivePointers.put(2,new PointF(750, 1070));
            mActivePointers.put(3,new PointF(985, 970));
            mActivePointers.put(4,new PointF(1090, 780));
            mActivePointers.put(5,new PointF(1870, 310));
            mActivePointers.put(6,new PointF(1560, 1340));

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return mActivePointers;
    }

        /**
         * Get the X for the current position.
         *
         * @param event
         * @return
         */
        public Float getXPosition(MotionEvent event) {
            Float x = event.getX();
            return x;
        }

        /**
         * Get the Y for the current position.
         *
         * @param event
         * @return
         */
        public Float getYPosition(MotionEvent event) {
            Float y = event.getY();
            return y;
        }

        /**
         * Build the parameter string.
         *
         * @param x
         * @param y
         * @return
         * @// TODO: 07/06/2017 refactor this. Think it is on the wrong spot
         */
        private String buildParams(Float x, Float y) {
            StringBuilder result = new StringBuilder();
            try {
                result.append(URLEncoder.encode("x", "UTF-8"));
                result.append("=");
                result.append(x.toString());
                result.append("&");
                result.append(URLEncoder.encode("y", "UTF-8"));
                result.append("=");
                result.append(y.toString());
            } catch (UnsupportedEncodingException ue) {
                Log.d("ENCODE", ue.getMessage());
            }
            return result.toString();
        }

}
