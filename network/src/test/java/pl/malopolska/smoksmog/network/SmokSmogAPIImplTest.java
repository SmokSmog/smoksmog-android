package pl.malopolska.smoksmog.network;

import org.junit.Test;

public class SmokSmogAPIImplTest {

    public static final String ENDPOINT = "http://www.onet.pl";

    @Test(expected = Exception.class)
    public void testConstructor(){
        new SmokSmogAPIImpl();
    }

    @Test
    public void testCreateValid() throws Exception {
        SmokSmogAPIImpl.create(ENDPOINT);
        SmokSmogAPIImpl.create(ENDPOINT + "/");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalid() throws Exception {
        SmokSmogAPIImpl.create(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalidSecond() throws Exception {
        SmokSmogAPIImpl.create(ENDPOINT, null);
    }
}