package pl.malopolska.smoksmog.network;

import junit.framework.TestCase;

import org.junit.Test;

public class SmokSmogAPICreatorTest extends TestCase {

    public static final String ENDPOINT = "http://www.onet.pl";

    public void testCreateValid() throws Exception {
        SmokSmogAPICreator.create(ENDPOINT);
        SmokSmogAPICreator.create(ENDPOINT + "/");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalid() throws Exception {
        SmokSmogAPICreator.create(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalidSecond() throws Exception {
        SmokSmogAPICreator.create(ENDPOINT, null);
    }
}