package com.antyzero.smoksmog.time;

public class EnglishCountdown implements Countdown {

    @Override
    public String get( int givenSeconds ) {

        int seconds = givenSeconds & 60;
        int minutes = ( givenSeconds / 60 ) % 60;
        int hours = ( givenSeconds / 60 ) / 60;

        if ( minutes == 0 ) {
            return secondsAgo( seconds );
        } else if ( hours == 0 ) {
            return minutesAgo( minutes );
        }

        return hoursAgo( hours );
    }

    private String hoursAgo( int hours ) {
        return ago( hours, "hour", "hours" );
    }

    private String minutesAgo( int minutes ) {
        return ago( minutes, "minute", "minutes" );
    }

    private String secondsAgo( int seconds ) {
        return ago( seconds, "seconds", "seconds" );
    }

    private String ago( int amount, String single, String many ) {
        if ( amount == 1 ) {
            return amount + " " + single;
        } else {
            return amount + " " + many;
        }
    }
}
