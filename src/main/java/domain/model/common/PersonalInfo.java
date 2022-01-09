package domain.model.common;

import shared.Entity;

import java.util.Date;

public class PersonalInfo implements Entity<PersonalInfo> {
    private Name name;
    private String nationalCode;
    private String id;
    private Date birthDate;

    public PersonalInfo(Name name, String nationalCode, String id, Date birthDate) {
        this.name = name;
        this.nationalCode = nationalCode;
        this.id = id;
        this.birthDate = birthDate;
    }

    public boolean sameIdentityAs(PersonalInfo other) {
        return this.name.sameValueAs(other.name) && this.nationalCode.equals(other.nationalCode)
                && this.birthDate.equals(other.birthDate) && this.id.equals(other.id);
    }

    public String getId() {
        return id;
    }
}
