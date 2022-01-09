package domain.model.educationChart.prerequisite;

import domain.model.course.Course;
import java.util.ArrayList;


abstract public class CoursePrerequisite implements Prerequisite{
    protected ArrayList<Course> courses;

    public CoursePrerequisite(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public ArrayList<Course> getCourses(){
        return this.courses;
    }
}
