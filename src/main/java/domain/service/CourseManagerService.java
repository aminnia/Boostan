package domain.service;

import domain.model.course.Course;
import domain.model.educationChart.Chart;
import domain.model.educationChart.exception.CourseNotFoundInStudentChartException;
import domain.model.offer.exception.PrerequisiteNotSatisfiedException;
import domain.model.offer.CourseOffering;
import domain.model.offer.exception.ClassCapacityFullException;
import domain.model.offer.exception.StudentNotAttendException;
import domain.model.register.Student;
import domain.model.register.exception.NotDeleteStudentCourseException;
import domain.model.register.exception.courseTakingException.CourseTakingException;

import java.util.ArrayList;

public class CourseManagerService {
    private static CourseManagerService ourInstance = new CourseManagerService();

    public CourseManagerService getInstance(){
        return ourInstance;
    }

    public void takeCourse(Student student, CourseOffering courseOffering)
            throws PrerequisiteNotSatisfiedException, ClassCapacityFullException,
            CourseTakingException, CourseNotFoundInStudentChartException {
        Chart studentChart = student.getChart();
        Course offeredCourse = courseOffering.getCourse();
        ArrayList<Course> requiredPassedCourse =
                studentChart.getRequiredPassedCourses(offeredCourse);
        ArrayList<Course> requiredCurrentCourse =
                studentChart.getRequiredCurrentCourses(offeredCourse);
        student.validatePrerequisite(requiredPassedCourse, requiredCurrentCourse);
        student.validateConditions(courseOffering);
        student.receiveCourse(courseOffering);
        courseOffering.addAttendee(student);
    }

    public void removeCourse(Student student, CourseOffering courseOffering)
            throws NotDeleteStudentCourseException, StudentNotAttendException {
        student.deleteCourse(courseOffering);
        courseOffering.deleteAttendee(student);
    }
}
