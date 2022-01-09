package domain.model.common;

import shared.ValueObject;

import java.util.Date;

public class DatePeriod implements ValueObject<DatePeriod> {
    private Date startDate;
    private Date endDate;

    public DatePeriod(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean sameValueAs(DatePeriod other) {
        return this.startDate.equals(other.startDate) && this.endDate.equals(other.endDate);
    }
}
