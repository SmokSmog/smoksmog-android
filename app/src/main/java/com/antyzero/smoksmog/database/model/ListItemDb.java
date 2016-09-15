package com.antyzero.smoksmog.database.model;


import com.google.auto.value.AutoValue;
import com.squareup.sqldelight.RowMapper;

@AutoValue
public abstract class ListItemDb implements ListItemDbModel {
    public static final Factory<ListItemDb> FACTORY = new Factory<>(AutoValue_ListItemDb::new);
    public static final RowMapper<ListItemDb> MAPPER = FACTORY.select_by_idMapper();
}
