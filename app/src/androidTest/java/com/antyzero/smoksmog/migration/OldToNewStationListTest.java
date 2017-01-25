package com.antyzero.smoksmog.migration;


import org.junit.Test;
import org.junit.runner.RunWith;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.antyzero.smoksmog.permission.PermissionHelper;
import com.antyzero.smoksmog.settings.SettingsHelper;
import com.antyzero.smoksmog.storage.JsonFileStorage;
import com.antyzero.smoksmog.storage.model.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public class OldToNewStationListTest {

    @SuppressLint("CommitPrefEdits")
    @Test
    public void simpleMigration() throws Exception {

        // Given
        List<Long> longList = new ArrayList<>(Arrays.asList(4L, 3L, 2L));
        Context context = InstrumentationRegistry.getTargetContext();
        SettingsHelper settingsHelper = new SettingsHelper(context);
        settingsHelper.getPreferences().edit().clear().commit();
        settingsHelper.setStationIdList(longList);
        JsonFileStorage jsonFileStorage = new JsonFileStorage();
        jsonFileStorage.clear();

        // When
        for (Long id : settingsHelper.getStationIdList()) {
            jsonFileStorage.addStation(id);
        }
        settingsHelper.getPreferences().edit().clear().commit();
        settingsHelper.getStationIdList().clear();

        // Then
        List<Item> items = jsonFileStorage.fetchAll();
        assertThat(items).hasSize(3);
        assertThat(items.get(0).getId()).isEqualTo(4);
        assertThat(items.get(1).getId()).isEqualTo(3);
        assertThat(items.get(2).getId()).isEqualTo(2);

        assertThat(settingsHelper.getStationIdList()).hasSize(0);
    }

}
