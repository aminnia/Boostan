package domain.model.educationChart;

import domain.model.course.Course;
import domain.model.educationChart.exception.CourseNotSameWithChartTermItemException;
import domain.model.educationChart.prerequisite.CoursePrerequisite;
import domain.model.educationChart.prerequisite.CoursesNeededPrerequisite;
import domain.model.educationChart.prerequisite.CoursesPassedPrerequisite;
import domain.model.educationChart.prerequisite.Prerequisite;
import shared.Entity;

import java.util.ArrayList;

public class ChartTermItem implements Entity<ChartTermItem> {
    private Course course;
    ArrayList<Prerequisite> prerequisites = new ArrayList<>();

    public ChartTermItem(Course course) {
        this.course = course;
    }

    @Override
    public boolean sameIdentityAs(ChartTermItem other){
        return this.course.sameValueAs(other.course);
    }

    public ArrayList<Course> getRequiredPassedCourse(Course offeredCourse)
            throws CourseNotSameWithChartTermItemException {
        if (this.course.sameValueAs(offeredCourse))
            for (Prerequisite prerequisite: this.prerequisites)
                if (prerequisite instanceof CoursesPassedPrerequisite)
                    return ((CoursePrerequisite)prerequisite).getCourses();
        throw new CourseNotSameWithChartTermItemException();
    }

    public ArrayList<Course> getRequiredCurrentCourse(Course offeredCourse)
            throws CourseNotSameWithChartTermItemException {
        if (this.course.sameValueAs(offeredCourse))
            for (Prerequisite prerequisite: this.prerequisites)
                if (prerequisite instanceof CoursesNeededPrerequisite)
                    return ((CoursePrerequisite)prerequisite).getCourses();
        throw new CourseNotSameWithChartTermItemException();
    }
}
