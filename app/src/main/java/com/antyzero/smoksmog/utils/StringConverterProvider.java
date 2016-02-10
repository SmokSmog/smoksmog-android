package com.antyzero.smoksmog.utils;

/**
 * Provides converters for strings
 */
public interface StringConverterProvider {
    <T> Converter<T> getConverterFor( Class<T> clazz );
}
