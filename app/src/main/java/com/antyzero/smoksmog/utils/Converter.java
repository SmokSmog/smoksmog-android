package com.antyzero.smoksmog.utils;

/**
 * For specyfic type conversion
 *
 * @param <T>
 */
public interface Converter<T> {
    T convert( String string );
}
