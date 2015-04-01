package pl.malopolska.smoksmog.ui;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;

import com.squareup.spoon.Spoon;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest() {
        super( MainActivity.class );
    }

    @UiThreadTest
    public void testCreation(){

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Spoon.screenshot( getActivity(), "Created" );
    }
}