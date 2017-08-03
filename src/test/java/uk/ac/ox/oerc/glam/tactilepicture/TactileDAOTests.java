package uk.ac.ox.oerc.glam.tactilepicture;

import android.graphics.PointF;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the DAO
 */

public class TactileDAOTests {

    @Test
    public void testDAOFileExists() {
        TactileDAO td = new TactileDAO();
        PointF point = new PointF(750, 1092);
        String audioFile = td.getAudio(point);
        assertEquals(audioFile,"ladder.mp3");
    }

    @Test
    public void testDAOFileNotExists() {
        TactileDAO td = new TactileDAO();
        PointF point = new PointF(400, 1092);
        String audioFile = td.getAudio(point);
        assertEquals(audioFile,"");
    }

    @Test
    public void testDAOFileStopTrue() {
        TactileDAO td = new TactileDAO();
        PointF point = new PointF(76, 220);
        boolean stop = td.getStop(point);
        assertTrue(stop);
    }

    @Test
    public void testDAOFileStopFalse() {
        TactileDAO td = new TactileDAO();
        PointF point = new PointF(50, 220);
        boolean stop = td.getStop(point);
        assertTrue(stop);
    }
}
