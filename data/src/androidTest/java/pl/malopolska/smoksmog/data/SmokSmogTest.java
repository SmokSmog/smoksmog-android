package pl.malopolska.smoksmog.data;

import android.app.Application;
import android.test.ApplicationTestCase;

import static org.assertj.core.api.Assertions.assertThat;

public class SmokSmogTest extends ApplicationTestCase<Application> {

    private SmokSmog smokSmog;

    public SmokSmogTest() {
        super(Application.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        createApplication();
        
        Application application = getApplication();

        smokSmog = new SmokSmog.Builder(application)
                .build();
    }

    public void testGetAPI() throws Exception {
        assertThat(smokSmog.getAPI()).isNotNull();
    }
}