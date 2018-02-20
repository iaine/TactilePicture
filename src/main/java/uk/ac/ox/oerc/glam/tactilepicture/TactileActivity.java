package uk.ac.ox.oerc.glam.tactilepicture;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class TactileActivity extends AppCompatActivity  {

    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // use the Exception Handler to do the restart
        //Thread.setDefaultUncaughtExceptionHandler(new TactileExceptionHandler(this));
        // keep the screen on whilst app running
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        /*if (getIntent().getBooleanExtra("crash", false)) {
            Toast.makeText(this, "App restarted after crash", Toast.LENGTH_SHORT).show();
        }*/
        //throw new NullPointerException();
        setContentView(new TactileView(this, null));
    }

    // overriding the Back Button
    public void onBackPressed() {

    }
}
