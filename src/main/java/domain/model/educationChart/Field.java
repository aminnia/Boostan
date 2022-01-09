package domain.model.educationChart;

import shared.ValueObject;

import java.util.ArrayList;

public class Field implements ValueObject<Field> {
    private String name;
    private ArrayList<Chart> charts;

    public Field(String name, ArrayList<Chart> charts) {
        this.name = name;
        this.charts = charts;
    }

    @Override
    public boolean sameValueAs(Field other) {
        return this.name.equals(other.name) && this.charts.equals(other.charts);
    }
}
