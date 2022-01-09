package domain.model.register;

import domain.model.course.Course;
import domain.model.offer.CourseOffering;
import domain.model.offer.exception.CoursesNeededPrerequisiteNotSatisfiedException;
import domain.model.offer.exception.CoursesPassedPrerequisiteNotSatisfiedException;
import domain.model.register.exception.*;
import domain.model.register.exception.courseTakingException.ConflictClassTimeException;
import domain.model.register.exception.courseTakingException.ConflictExamTimeException;
import domain.model.register.exception.courseTakingException.DuplicateOfferingCourseTakenException;
import shared.ValueObject;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;

import static domain.model.utility.Utility.timeHasConflict;

public class EnrolledCourse implements ValueObject<EnrolledCourse> {
    enum CourseState {REJECTED, PASSED, TAKEN, DELETED}
    private CourseOffering courseOffering;
    private CourseState state;
    private float score;

    public EnrolledCourse(CourseOffering course) {
        this.courseOffering = course;
        this.state = CourseState.TAKEN;
    }

    public float getScore() throws NotGradedCourseException {
        if (this.state.equals(CourseState.DELETED))
            throw new DeletedCourseException();
        else if (this.state.equals(CourseState.TAKEN))
            throw new CurrentTermTakenCourseException();
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public boolean sameValueAs(EnrolledCourse other) {
        return this.courseOffering.sameIdentityAs(other.courseOffering) && this.state.equals(other.state);
    }

    public boolean isPassed() {
        return this.state.equals(CourseState.PASSED);
    }

    public boolean isTaken() {
        return this.state.equals(CourseState.TAKEN);
    }

    public Course getCourse() {
        return this.courseOffering.getCourse();
    }

    public float getTotalNumberOfUnits() {
        return this.courseOffering.getTotalNumberOfUnits();
    }

    public CourseOffering getCourseOffering() {
        return this.courseOffering;
    }

    public void validateClassTimeConflict(CourseOffering courseOffering)
            throws ConflictClassTimeException {
        Time classStartTime = this.courseOffering.getClassStartTimeSlot();
        Time classEndTime = this.courseOffering.getClassEndTimeSlot();
        ArrayList<DayOfWeek> classDaysOfWeek = this.courseOffering.getWeekdays();

        Time newClassStartTime = courseOffering.getClassStartTimeSlot();
        Time newClassEndTime = courseOffering.getClassEndTimeSlot();
        ArrayList<DayOfWeek> newClassDaysOfWeek = courseOffering.getWeekdays();

        timeHasConflict(classStartTime, classEndTime, classDaysOfWeek, newClassStartTime,
                newClassEndTime, newClassDaysOfWeek);
    }

    public void validateExamTimeConflict(CourseOffering courseOffering)
            throws ConflictExamTimeException {
        Time classStartTime = this.courseOffering.getExamStartTimeSlot();
        Time classEndTime = this.courseOffering.getExamEndTimeSlot();
        Date ExamDate = this.courseOffering.getExamDate();

        Time newClassStartTime = courseOffering.getExamStartTimeSlot();
        Time newClassEndTime = courseOffering.getExamEndTimeSlot();
        Date newExamDate  = courseOffering.getExamDate();

        timeHasConflict(classStartTime, classEndTime, ExamDate, newClassStartTime,
                newClassEndTime, newExamDate);
    }


    public void validateDuplicateOfferingCourse(CourseOffering courseOffering)
            throws DuplicateOfferingCourseTakenException {
        if (this.courseOffering.sameIdentityAs(courseOffering))
            throw new DuplicateOfferingCourseTakenException();
    }

    public String getCourseName() {
        return this.courseOffering.getCourseName();
    }


    public void validatePassedPrerequisite(Course requiredPassedCourse)
            throws CoursesPassedPrerequisiteNotSatisfiedException {
        this.courseOffering.validatePassedPrerequisite(requiredPassedCourse);
    }

    public void validateCurrentPrerequisite(Course requiredCurrentCourse)
            throws CoursesNeededPrerequisiteNotSatisfiedException {
        this.courseOffering.validateCurrentPrerequisite(requiredCurrentCourse);
    }

}
