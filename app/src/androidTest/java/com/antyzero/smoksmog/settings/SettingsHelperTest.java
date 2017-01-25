package com.antyzero.smoksmog.settings;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SettingsHelperTest {

    private SettingsHelper settingsHelper;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        settingsHelper = new SettingsHelper(context);
    }
}