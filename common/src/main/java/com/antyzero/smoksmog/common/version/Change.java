package com.antyzero.smoksmog.common.version;

/**
 * Created by iwopolanski on 12.03.16.
 */
public class Change {

    String name;

    Type type = Type.DEFAULT;

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        NEW, FIX, DEFAULT
    }
}
