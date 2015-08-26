package pl.malopolska.smoksmog.model;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import lombok.Getter;

@Getter
public class History {

    private float value;
    private LocalDate date;
}
