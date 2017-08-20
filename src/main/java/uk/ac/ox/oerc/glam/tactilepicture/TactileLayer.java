package uk.ac.ox.oerc.glam.tactilepicture;

/**
 * Created by iain.emsley on 20/08/2017.
 */

public class TactileLayer {

    public enum TactileLayers {
        ONE, TWO
    }

    TactileLayers tactileLayers;

    public void setTactileLayers (TactileLayers tl) {
        tactileLayers = tl;
    }

    public TactileLayers getTactileLayers () {
        return tactileLayers;
    }
}
