package pl.malopolska.smoksmog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Simple robolectric test
 */
@RunWith( RobolectricTestRunner.class )
public class DumbRoboTest {

    @Test
    public void testTruth() throws Exception {
        
        assertThat(true).isTrue();
    }
}
