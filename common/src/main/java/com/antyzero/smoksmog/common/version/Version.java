package com.antyzero.smoksmog.common.version;


import java.util.Collection;

public class Version {

    Collection<ChangeSet> changeSets;

    String versionName;
    String releaseDate;

    long versionCode;

    public String getVersionName() {
        return versionName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public long getVersionCode() {
        return versionCode;
    }

    public Collection<ChangeSet> getChangeSets() {
        return changeSets;
    }
}
