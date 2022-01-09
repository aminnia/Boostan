package domain.model.offer;

import shared.ValueObject;

import java.sql.Time;

public class TimeSlot implements ValueObject<TimeSlot> {
    private Time start;
    private Time end;

    public TimeSlot(Time start, Time end) {
        this.start = start;
        this.end = end;
    }

    public boolean sameValueAs(TimeSlot other) {
        return this.start.equals(other.start) && this.end.equals(other.end);
    }

    public Time getStart() {
        return start;
    }

    public Time getEnd() {
        return end;
    }
}
