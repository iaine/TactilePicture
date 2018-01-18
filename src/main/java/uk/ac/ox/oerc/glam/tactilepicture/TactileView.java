package uk.ac.ox.oerc.glam.tactilepicture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Handles the touch events.
 */

public class TactileView extends View  {


    public SparseArray<PointF> mActivePointers;

    private Paint mPaint;

    private GestureDetectorCompat mDetector;

    public boolean button;

    public TactileAudio tactileAudio;

    public TactileView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        tactileAudio = new TactileAudio();
        DoubleGesture dgl = new DoubleGesture();
        mDetector = new GestureDetectorCompat(context, dgl);
        mDetector.setOnDoubleTapListener(dgl);
        button = false;
        initView();

    }

    /**
     * Function to set up the circles tobe drawn.
     */
    private void initView() {
        mActivePointers = new SparseArray<PointF>();
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        //float width = _tmp.getWidth();
        //float height = this.getHeight();

        mActivePointers.put(1,new PointF(54, 82)); //start
        mActivePointers.put(2,new PointF(54, 187)); //stop
        mActivePointers.put(3,new PointF(46, 448));
        mActivePointers.put(4,new PointF(535, 446));
        mActivePointers.put(5,new PointF(1270, 575));
        mActivePointers.put(6,new PointF(630, 818));
        mActivePointers.put(7,new PointF(50, 1307));
        mActivePointers.put(8,new PointF(301,1755));
        mActivePointers.put(9,new PointF(1444, 1235));
        /*
        Test code for the
        float newWidth = (float)(width * 0.08677685950413223);
        //float newHeight = (float)(height * 0.164021164021164);
        //mActivePointers.put(1,new PointF(this.setPoint(0.08677685950413223, width), this.setPoint(0.164021164021164, height)));
        //Log.d("Device", "width: " + width);
        //Log.d("Device", "height: " + height);*/
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.CYAN);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }

    /**
     * Function to calculate the return point
     * @param arrayPoint
     * @param dimension
     * @return
     */
    private float setPoint(double arrayPoint, int dimension) {
        return (float)(arrayPoint * dimension);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /*Log.d("Gesture", "Masked Event: " + MotionEvent.actionToString(event.getActionMasked()));
        Log.d("Gesture", "Event: " + MotionEvent.actionToString(event.getAction()));
        Log.d("Gesture", "Log state is " + mDetector.onTouchEvent(event));
        if (mDetector.onTouchEvent(event) == true) {
            Log.d("Gesture", "inside loop");
            switch(event.getActionMasked()){
                case MotionEvent.ACTION_POINTER_DOWN:
                case MotionEvent.ACTION_DOWN:
                    tactileAudio.setAudio(this.findEventPoint(event));
            }
        }*/
        mDetector.onTouchEvent(event);
        return true;
    }

    private PointF findEventPoint(MotionEvent e) {
        int pointer = e.getActionIndex();
        int pointerId = e.getPointerId(pointer);
        Log.d("Gesture", "x " + this.getXPosition(e, pointerId) + " y " + this.getYPosition(e, pointerId));
        PointF point = new PointF(this.getXPosition(e, pointerId), this.getYPosition(e, pointerId));
        Log.d("Gesture", "Pointer is " + pointerId);
        return point;
    }

    private float getYPosition( MotionEvent e, int pointer) {
        return e.getY(pointer);
    }

    private float getXPosition( MotionEvent e, int pointer) {
        return e.getX(pointer);
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

    class DoubleGesture extends GestureDetector.SimpleOnGestureListener
            implements GestureDetector.OnDoubleTapListener  {

        TactileAudio tactileAudio;
        boolean astate;
        protected DoubleGesture() {
            this.tactileAudio  = new TactileAudio();
            astate = false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            this.tactileAudio.setAudio(this.findEventPoint(e));
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            this.tactileAudio.setAudio(this.findEventPoint(e));
            return false;
        }

        private PointF findEventPoint(MotionEvent e) {
            int pointer = e.getActionIndex();
            int pointerId = e.getPointerId(pointer);
            Log.d("Gesture", "x " + this.getXPosition(e, pointerId) + " y " + this.getYPosition(e, pointerId));
            PointF point = new PointF(this.getXPosition(e, pointerId), this.getYPosition(e, pointerId));
            Log.d("Gesture", "Pointer is " + pointerId);
            return point;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }

        public void onLongPress(MotionEvent e) {
            //Log.d("Gesture", "Long press detected");
            this.tactileAudio.setAudio(this.findEventPoint(e));
        }

        private float getXPosition( MotionEvent e, int pointer) {
            return e.getX(pointer);
        }

        private float getXPosition( MotionEvent e) {
            return e.getX();
        }

        private float getYPosition( MotionEvent e, int pointer) {
            return e.getY(pointer);
        }

        private float getYPosition( MotionEvent e) {
        return e.getY();
    }
    };
}
