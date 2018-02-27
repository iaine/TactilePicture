package uk.ac.ox.oerc.glam.tactilepicture;


import android.app.DialogFragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
        Thread.setDefaultUncaughtExceptionHandler(new TactileExceptionHandler(this));
        // keep the screen on whilst app running
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //check  for connectivty. Only show the upload dialogue if WiFi is on.
        ConnectivityManager connManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifi.isConnected()) {
            showDialog();
        }

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
