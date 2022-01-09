package domain.model.course;

import domain.model.common.Term;
import domain.model.common.DataNotExistInDatabase;
import domain.model.offer.CourseOffering;
import domain.model.offer.Teacher;

import java.util.ArrayList;

public class CourseRepository {
    private static CourseRepository ourInstance = new CourseRepository();
    public CourseRepository getInstance(){
        return ourInstance;
    }

    private ArrayList<Course> courses = new ArrayList<>();
    private ArrayList<CourseOffering> courseOfferings = new ArrayList<>();
    private ArrayList<Teacher> teachers = new ArrayList<>();

    public Course findCourse(String name) throws DataNotExistInDatabase {
        for (Course course: this.courses)
            if (course.getName().equals(name))
                return course;
        throw new DataNotExistInDatabase();
    }

    public void insertCourse(Course course){
        this.courses.add(course);
    }

    public CourseOffering findCourseOffering(Term term, int classNumber)
            throws DataNotExistInDatabase {
        for (CourseOffering courseOffering: this.courseOfferings)
            if (courseOffering.getTerm().equals(term) &&
                    courseOffering.getClassNumber() == classNumber)
                return courseOffering;
        throw new DataNotExistInDatabase();
    }

    public void insertCourseOffering(CourseOffering courseOffering){
        this.courseOfferings.add(courseOffering);
    }

    public void insertCourse(Teacher teacher){
        this.teachers.add(teacher);
    }
}
