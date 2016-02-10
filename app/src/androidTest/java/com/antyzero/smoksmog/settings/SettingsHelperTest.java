package com.antyzero.smoksmog.settings;

import android.app.Application;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith( AndroidJUnit4.class )
@SmallTest
public class SettingsHelperTest extends ApplicationTestCase<Application> {

    public SettingsHelperTest( ) {
        super( Application.class );
    }

    @Test
    public void testGetStationIdList() throws Exception {

        // Given
        SettingsHelper settingsHelper = new SettingsHelper( InstrumentationRegistry.getContext() );
        List<Long> longList = new ArrayList<>( Arrays.asList( 4L, 3L, 2L ) );
        settingsHelper.setStationIdList( longList );

        // When
        List<Long> result = settingsHelper.getStationIdList();

        // Then
        assertThat( result ).containsSequence( 4L, 3L, 2L );
    }
}