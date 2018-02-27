package uk.ac.ox.oerc.glam.tactilepicture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
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

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Handles the touch events.
 */

public class TactileView extends View  {


    public SparseArray<PointF> mActivePointers;

    private Paint mPaint;

    private GestureDetectorCompat mDetector;

    public boolean button;

    public TactileAudio tactileAudio;

    public Context context;

    public JSONArray jsonArray;

    public int width;

    public int height;

    public TactileView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        tactileAudio = new TactileAudio();
        DoubleGesture dgl = new DoubleGesture();
        mDetector = new GestureDetectorCompat(context, dgl);
        mDetector.setOnDoubleTapListener(dgl);
        button = false;

        //check for connection
        //setUpData(context);
        initView();
    }

    /**
     * Method to load data
     * If the wifi is on, then load the url and write the
     * data form the disk
     * @param context - the system context
     */
    private void setUpData(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifi.isConnected()){
            GetJSON getJSON = new GetJSON();
            Log.d("File", "Getting data");
            getJSON.getJSON("http://demeter.oerc.ox.ac.uk/glam/record/turner_image/json");
        }
    }

    /**
     * Function to set up the circles tobe drawn.
     */
    private void initView() {
        mActivePointers = new SparseArray<PointF>();
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;

        try {
            JSONObject json = getDataforInit();
            jsonArray = json.getJSONArray("points");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);
                mActivePointers.put(i, new PointF(this.setPoint(c.optDouble("x"), width),
                        this.setPoint(c.optDouble("y"), height)));
            }

        } catch (JSONException je) {
            Log.d("File", "JSON exception :" + je.toString());
        } catch (Exception e) {
            Log.d("File", "Other exception :" + e.toString());
        }

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.CYAN);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }

    private JSONObject getDataforInit() {
        JSONObject jsonObject = null;
        try {
            GetJSON getJSON = new GetJSON();
            jsonObject = getJSON.ParseJSON();
        } catch (Exception e) {
            Log.d("File", "getData: " + e.toString());
        }
        return jsonObject;
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

    /**
     * Method to handle the double touch gesture event.
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return true;
    }

    /**
     * Method to get the point within the
     * @param e
     * @return
     */
    private PointF findEventPoint(MotionEvent e) {
        int pointer = e.getActionIndex();
        int pointerId = e.getPointerId(pointer);
        PointF point = new PointF(this.getXPosition(e, pointerId), this.getYPosition(e, pointerId));
        return point;
    }

    /**
     * Method to get the Y position from the event and the pointer setting it off
     * @param e
     * @param pointer
     * @return
     */
    private float getYPosition( MotionEvent e, int pointer) {
        return e.getY(pointer);
    }

    /**
     * Method to get the X position from the event and the pointer setting it off
     * @param e
     * @param pointer
     * @return
     */
    private float getXPosition( MotionEvent e, int pointer) {
        return e.getX(pointer);
    }

    /**
     * Method to create the dots on the screen.
     * @param canvas
     */
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

    /*private SparseArray getPoints(SparseArray mActivePointers) {

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
    }*/

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
            this.tactileAudio.setAudio(this.findEventPoint(e), jsonArray, width, height);
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            this.tactileAudio.setAudio(this.findEventPoint(e), jsonArray, width, height);
            return false;
        }

        private PointF findEventPoint(MotionEvent e) {
            int pointer = e.getActionIndex();
            int pointerId = e.getPointerId(pointer);
            PointF point = new PointF(this.getXPosition(e, pointerId), this.getYPosition(e, pointerId));
            return point;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }

        public void onLongPress(MotionEvent e) {
            this.tactileAudio.setAudio(this.findEventPoint(e), jsonArray, width, height);
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
