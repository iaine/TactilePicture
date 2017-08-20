package uk.ac.ox.oerc.glam.tactilepicture;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests to set the layers for audio files
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
        assertEquals("TWO", tl.getTactileLayers().name());
    }
}
