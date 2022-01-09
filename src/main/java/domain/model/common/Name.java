package domain.model.common;

import shared.ValueObject;

public class Name implements ValueObject<Name> {
    private String firstName;
    private String lastName;

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean sameValueAs(Name other) {
        return this.firstName.equals(other.firstName) && this.lastName.equals(other.lastName);
    }
}
