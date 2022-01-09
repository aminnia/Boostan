package domain.model.educationChart.prerequisite;

import domain.model.course.Course;
import domain.model.offer.exception.CoursesNeededPrerequisiteNotSatisfiedException;
import domain.model.offer.exception.PrerequisiteNotSatisfiedException;

import java.util.ArrayList;

public class CoursesNeededPrerequisite extends CoursePrerequisite {
    public CoursesNeededPrerequisite(ArrayList<Course> courses) {
        super(courses);
    }

    @Override
    public void validate(ArrayList<Course> neededCourses) throws PrerequisiteNotSatisfiedException {
        for(Course neededCourse: neededCourses)
            if(!this.courses.contains(neededCourse))
                throw new CoursesNeededPrerequisiteNotSatisfiedException();
    }
}
