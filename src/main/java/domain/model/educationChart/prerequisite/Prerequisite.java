package domain.model.educationChart.prerequisite;

import domain.model.course.Course;
import domain.model.offer.exception.PrerequisiteNotSatisfiedException;

import java.util.ArrayList;

public interface Prerequisite {
    void validate(ArrayList<Course> courses) throws PrerequisiteNotSatisfiedException;
}
