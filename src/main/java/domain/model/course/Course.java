package domain.model.course;

import domain.model.educationChart.prerequisite.CoursesNeededPrerequisite;
import domain.model.educationChart.prerequisite.Prerequisite;
import domain.model.offer.exception.PrerequisiteNotSatisfiedException;
import shared.ValueObject;

import java.util.ArrayList;

public class Course implements ValueObject<Course> {
    private String name;
    private NumOfUnits units;
    private ArrayList<Prerequisite> prerequisites;

    public Course(String name, NumOfUnits units, ArrayList<Prerequisite> prerequisites) {
        this.name = name;
        this.units = units;
        this.prerequisites = prerequisites;
    }

    public boolean sameValueAs(Course other) {
        return this.name.equals(other.name) && this.units.sameValueAs(other.units);
    }

    public ArrayList<Prerequisite> getPrerequisites() {
        return prerequisites;
    }

    public float getTotalNumOfUnits(){
        return this.units.getPractical() + this.units.getTheoretical();
    }

    public void validatePrerequisites(ArrayList<Course> currentCourses,
                                      ArrayList<Course> passedCourses)
            throws PrerequisiteNotSatisfiedException{
        for (Prerequisite prerequisite: this.prerequisites) {
            if (prerequisite instanceof CoursesNeededPrerequisite) {
                ArrayList<Course> totalCourses = new ArrayList<>();
                totalCourses.addAll(currentCourses);
                totalCourses.addAll(passedCourses);
                prerequisite.validate(totalCourses);
            }
            else prerequisite.validate(passedCourses);
        }
    }

    public String getName() {
        return name;
    }
}
