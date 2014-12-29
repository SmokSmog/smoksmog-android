package pl.malopolska.smoksmog.network;

import junit.framework.TestCase;

public class SmokSmogAPICreatorTest extends TestCase {

    public static final String ENDPOINT = "http://www.onet.pl";

    public void testCreateValid() throws Exception {
        SmokSmogAPICreator.create( ENDPOINT );
    }

    public void testCreateInvalid() throws Exception {
        SmokSmogAPICreator.create( null, null );
    }

    public void testCreateInvalidSecond() throws Exception {
        SmokSmogAPICreator.create(ENDPOINT, null );
    }
}