package uk.ac.ox.oerc.glam.tactilepicture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.MediaPlayer;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Handles the touch events.
 */

public class TactileView extends View  {


    public SparseArray<PointF> mActivePointers;

    private Paint mPaint;

    private GestureDetectorCompat mDetector;

    public TactileView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TactileAudio tactileAudio = new TactileAudio();
        MyGestureListener mgl = new MyGestureListener();
        final GestureDetector.OnDoubleTapListener listener = new DoubleGesture();
        mDetector = new GestureDetectorCompat(context, mgl);
        mDetector.setOnDoubleTapListener(listener);
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
        mActivePointers.put(7,new PointF(100, 60));
        mActivePointers.put(8,new PointF(100, 220));
        mActivePointers.put(9,new PointF(915, 1345));

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.CYAN);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        mDetector.onTouchEvent(event);

        return true;
    }

        /**
         * Fires on up motion
         *
         * @param
         * @return
         */
    /*public boolean onPress(MotionEvent e) {
            /**
             * If x is less 0.10 and y is less that 0.10, state == 3, reset time
             */
            /*Log.d("Position", "x: " + this.getXPosition(e) + "y: " + this.getYPosition(e));
            if (this.getXPosition(e) < 100) {
                //overview audio
                if (this.getYPosition(e) < 150) {
                    if (System.currentTimeMillis() > (cmdTime + (2 * 60 * 1000))) {
                        this.startCommand("overall.mp3");
                    } else {
                        this.stopCommand();
                    }
                } else if (this.getYPosition(e) < 280) {
                    //stop command
                    this.stopCommand();
                }
            } else {
                this.getAudio(e);
            }
            return true;
        }*/

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

    class DoubleGesture implements GestureDetector.OnDoubleTapListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            PointF point = new PointF(this.getXPosition(e), this.getYPosition(e));
            //tactileAudio.setAudio(point);
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }

        private float getXPosition( MotionEvent e) {
        return e.getX();
    }

        private float getYPosition( MotionEvent e) {
        return e.getY();
    }
    };

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";


        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d(DEBUG_TAG, "kittens");
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }
    }
}
