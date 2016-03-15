package com.antyzero.smoksmog.common.version;


import java.util.List;
import java.util.Locale;

public class ChangeSet {

    Locale language;

    List<Change> changes;

    public Locale getLanguage() {
        return language;
    }

    public List<Change> getChanges() {
        return changes;
    }
}
