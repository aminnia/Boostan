package domain.model.educationChart.prerequisite;

import domain.model.course.Course;
import domain.model.offer.exception.CoursesPassedPrerequisiteNotSatisfiedException;
import domain.model.offer.exception.PrerequisiteNotSatisfiedException;

import java.util.ArrayList;


public class CoursesPassedPrerequisite extends CoursePrerequisite{

    public CoursesPassedPrerequisite(ArrayList<Course> courses) {
        super(courses);
    }

    @Override
    public void validate(ArrayList<Course> passedCourses) throws PrerequisiteNotSatisfiedException {
        for(Course passedCourse: passedCourses)
            if(!this.courses.contains(passedCourse))
                throw new CoursesPassedPrerequisiteNotSatisfiedException();
    }

}
