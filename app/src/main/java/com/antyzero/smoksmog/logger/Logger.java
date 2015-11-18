package com.antyzero.smoksmog.logger;


public interface Logger {

    // Verbose

    void v( String tag, String message );

    void v( String tag, String message, Throwable throwable );

    // Debug

    void d( String tag, String message );

    void d( String tag, String message, Throwable throwable );

    // Info

    void i( String tag, String message );

    void i( String tag, String message, Throwable throwable );

    // Warning

    void w( String tag, String message );

    void w( String tag, String message, Throwable throwable );

    // Error

    void e( String tag, String message );

    void e( String tag, String message, Throwable throwable );
}
