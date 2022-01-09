package domain.model.common;

import shared.ValueObject;

public class Term implements ValueObject<Term> {
    private DatePeriod period;
    private DatePeriod registrationPeriod;
    private String name;
    private int termNumber;

    public Term(DatePeriod period, DatePeriod registrationPeriod, String name, int termNumber) {
        this.period = period;
        this.registrationPeriod = registrationPeriod;
        this.name = name;
        this.termNumber = termNumber;
    }

    public boolean sameValueAs(Term other) {
        return false;
    }

    @Override
    public boolean equals(Object other){
        if (!(other instanceof Term))
            return false;
        return this.sameValueAs((Term) other);
    }
}
