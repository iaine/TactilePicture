package uk.ac.ox.oerc.glam.tactilepicture;


import android.app.DialogFragment;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.WindowManager;


public class TactileActivity extends AppCompatActivity {

    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // use the Exception Handler to do the restart
        //Thread.setDefaultUncaughtExceptionHandler(new TactileExceptionHandler(this));
        // keep the screen on whilst app running
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        showDialog();

        setContentView(new TactileView(this, null));
    }

    // overriding the Back Button
    public void onBackPressed() {

    }

    void showDialog() {
        DialogFragment newFragment = MyAlertDialogFragment.newInstance();
        newFragment.show(getFragmentManager(), "dialog");
    }


}
