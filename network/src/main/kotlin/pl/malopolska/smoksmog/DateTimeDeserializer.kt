package pl.malopolska.smoksmog

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.lang.reflect.Type
import java.util.*

class DateTimeDeserializer : JsonDeserializer<DateTime> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): DateTime {

        val input = json.asString

        val matcher = "(\\d+)-(\\d+)-(\\d+)\\s+?(\\d+):(\\d+):(\\d+)".toPattern().matcher(input)

        if (!matcher.matches()) {
            throw JsonParseException("Invalid date format")
        }

        val year = Integer.parseInt(matcher.group(1))
        val month = Integer.parseInt(matcher.group(2))
        val day = Integer.parseInt(matcher.group(3))
        val hour = Integer.parseInt(matcher.group(4))
        val minute = Integer.parseInt(matcher.group(5))
        val second = Integer.parseInt(matcher.group(6))

        val dateTime = DateTime.now(DateTimeZone.forTimeZone(TimeZone.getTimeZone("Europe/Warsaw")))
                .withYear(year)
                .withMonthOfYear(month)
                .withDayOfMonth(day)
                .withHourOfDay(hour)
                .withMinuteOfHour(minute)
                .withSecondOfMinute(second)

        return dateTime
    }
}
