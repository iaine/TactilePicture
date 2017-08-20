package uk.ac.ox.oerc.glam.tactilepicture;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by iain.emsley on 20/08/2017.
 */

public class testTactileLayer {

    @Test
    public void testSetLayers () {
        TactileLayer tl = new TactileLayer();
        assertEquals("ONE", TactileLayer.TactileLayers.ONE.name());
    }

    @Test
    public void testGetLayers () {
        TactileLayer tl = new TactileLayer();
        assertEquals("ONE", TactileLayer.TactileLayers.ONE.name());
        tl.setTactileLayers(TactileLayer.TactileLayers.TWO);
        assertEquals("TWO", tl.getTactileLayers());
    }
}
