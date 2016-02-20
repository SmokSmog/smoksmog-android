package com.antyzero.smoksmog.utils;

/**
 * Created by iwopolanski on 10.02.16.
 */
public class Convert implements StringConverterProvider {

    @Override
    public <T> Converter getConverterFor( Class<T> clazz ) {

        if( clazz.equals( Integer.class ) ){
            return Integer::valueOf;
        }
        if( clazz.equals( Long.class )){
            return Long::valueOf;
        }
        if( clazz.equals( Float.class )){
            return Float::valueOf;
        }
        if( clazz.equals( Double.class )){
            return Double::valueOf;
        }

        throw new IllegalArgumentException( "Unsupported type " + clazz );
    }
}
