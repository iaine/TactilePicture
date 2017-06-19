package uk.ac.ox.oerc.glam.tactilepicture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class TactileActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TactileView(this, null));
    }


}
