package com.antyzero.smoksmog.settings;

import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.antyzero.smoksmog.SmokSmogApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SmallTest
public class SettingsHelperTest extends ApplicationTestCase<SmokSmogApplication> {

    public SettingsHelperTest() {
        super( SmokSmogApplication.class );
    }

    public void testGetStationIdList() throws Exception {

        // Given
        createApplication();
        SettingsHelper settingsHelper = new SettingsHelper( getApplication() );
        List<Long> longList = new ArrayList<>( Arrays.asList( 4L, 3L, 2L ) );
        settingsHelper.setStationIdList( longList );

        // When
        List<Long> result = settingsHelper.getStationIdList();

        // Then
        assertThat( result ).containsSequence( 4L, 3L, 2L );
    }
}