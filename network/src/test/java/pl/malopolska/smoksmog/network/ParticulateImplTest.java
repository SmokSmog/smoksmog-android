package pl.malopolska.smoksmog.network;

import junit.framework.TestCase;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class ParticulateImplTest extends TestCase {

    private ParticulateImpl particulate;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        particulate = new ParticulateImpl();
    }

    /**
     * Testing workaround, any setter should grab any key stating with KEY_SHORT_NAME and assign
     * given value to KEY_SHORT_NAME
     */
    public void testAnySetter() {

        String newValue = "new value";
        String random = String.valueOf( new Random().nextInt( Integer.MAX_VALUE ) );

        particulate.anySetter( ParticulateImpl.KEY_SHORT_NAME + random, newValue );

        assertThat( particulate.getShortName() ).isEqualTo( newValue );
    }
}