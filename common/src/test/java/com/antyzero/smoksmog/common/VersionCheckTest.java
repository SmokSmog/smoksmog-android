package com.antyzero.smoksmog.common;


import com.antyzero.smoksmog.common.version.VersionCollection;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStreamReader;
import java.util.Locale;

public class VersionCheckTest {

    private VersionCollection versions;

    @Before
    public void setUp() throws Exception {
        InputStreamReader reader = new InputStreamReader(getClass().getResource("/versions.json").openStream());
        Gson gson = new Gson();
        versions = gson.fromJson(reader, VersionCollection.class);

        String.valueOf(versions);
    }

    @Test
    public void testCreation() throws Exception {
        new VersionCheck(new Locale("pl"), versions);
    }
}
