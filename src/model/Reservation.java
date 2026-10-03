package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private final Space space;
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public Reservation(
            Space space,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime
    ) {
        this.space = space;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Space getSpace() {
        return space;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}