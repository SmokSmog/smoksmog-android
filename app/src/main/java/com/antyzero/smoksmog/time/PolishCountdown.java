package com.antyzero.smoksmog.time;

public class PolishCountdown implements Countdown {

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
        return ago( hours, "godzinę", "godziny", "godzin" );
    }

    private String minutesAgo( int minutes ) {
        return ago( minutes, "minutę", "minuty", "minut" );
    }

    private String secondsAgo( int seconds ) {
        return ago( seconds, "sekundę", "sekundy", "sekund" );
    }

    private String ago( int amount, String single, String some, String many ) {
        if ( amount == 1 ) {
            return amount + " " + single;
        } else if ( endsWithTwoToFour( amount ) ) {
            return amount + " " + some;
        } else {
            return amount + " " + many;
        }
    }

    private boolean endsWithTwoToFour( int seconds ) {
        int modulo = seconds % 10;
        if ( seconds >= 10 && seconds < 20 ) {
            return false;
        } else if ( modulo >= 2 && modulo <= 4 ) {
            return true;
        }
        return false;
    }
}
