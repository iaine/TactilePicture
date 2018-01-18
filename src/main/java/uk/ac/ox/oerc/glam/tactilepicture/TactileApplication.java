package uk.ac.ox.oerc.glam.tactilepicture;

import android.content.Context;
import android.app.Application;

/**
 * Create the context object for the threading.
 */

public class TactileApplication extends Application {
    private static Context mContext;

    public static TactileApplication instance;

    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static TactileApplication getInstance() {
        return instance;
    }
}
