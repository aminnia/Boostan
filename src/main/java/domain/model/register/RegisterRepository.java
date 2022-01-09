package domain.model.register;

import domain.model.common.Term;
import domain.model.course.Course;
import domain.model.common.DataNotExistInDatabase;

import java.util.ArrayList;

public class RegisterRepository {
    private static RegisterRepository ourInstance = new RegisterRepository();
    public RegisterRepository getInstance(){
        return ourInstance;
    }

    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<AcademicRecord> academicRecords = new ArrayList<>();
    private ArrayList<EnrolledCourse> enrolledCourses = new ArrayList<>();

    public Student findStudent(String id) throws DataNotExistInDatabase {
        for (Student student: this.students)
            if (student.getId().equals(id))
                return student;
        throw new DataNotExistInDatabase();
    }

    public AcademicRecord findRegistration(Term term) throws DataNotExistInDatabase {
        for (AcademicRecord academicRecord : this.academicRecords)
            if (academicRecord.getTerm().sameValueAs(term))
                return academicRecord;
        throw new DataNotExistInDatabase();
    }

    public EnrolledCourse findReceivedCourse(Course course) throws DataNotExistInDatabase {
        for (EnrolledCourse enrolledCourse : this.enrolledCourses)
            if (enrolledCourse.getCourse().sameValueAs(course))
                return enrolledCourse;
        throw new DataNotExistInDatabase();
    }

    public void insertReceivedCourse(EnrolledCourse enrolledCourse){
        this.enrolledCourses.add(enrolledCourse);
    }

}
