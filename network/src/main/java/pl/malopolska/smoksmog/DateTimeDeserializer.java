package pl.malopolska.smoksmog;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.lang.reflect.Type;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeDeserializer implements JsonDeserializer<DateTime> {

    private static final Pattern DATE_TIME_PATTERN = Pattern.compile( "(\\d+)-(\\d+)-(\\d+)\\s+?(\\d+):(\\d+):(\\d+)" );

    @Override
    public DateTime deserialize( JsonElement json, Type typeOfT, JsonDeserializationContext context ) throws JsonParseException {

        Matcher matcher = DATE_TIME_PATTERN.matcher( json.getAsString() );

        if(!matcher.matches()){
            throw new JsonParseException( "Invalid date format" );
        }

        int year = Integer.parseInt( matcher.group( 1 ) );
        int month = Integer.parseInt( matcher.group( 2 ) );
        int day = Integer.parseInt( matcher.group( 3 ) );
        int hour = Integer.parseInt( matcher.group( 4 ) );
        int minute = Integer.parseInt( matcher.group( 5 ) );
        int second = Integer.parseInt( matcher.group( 6 ) );

        DateTime dateTime = DateTime.now( DateTimeZone.forTimeZone( TimeZone.getTimeZone( "Europe/Warsaw" ) ) )
                .withYear( year )
                .withMonthOfYear( month )
                .withDayOfMonth( day )
                .withHourOfDay( hour )
                .withMinuteOfHour( minute )
                .withSecondOfMinute( second );

        return dateTime;
    }
}
