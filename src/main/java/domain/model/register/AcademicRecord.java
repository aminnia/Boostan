package domain.model.register;

import domain.model.common.Term;
import domain.model.course.Course;
import domain.model.offer.CourseOffering;
import domain.model.offer.exception.ClassCapacityFullException;
import domain.model.offer.exception.CoursesNeededPrerequisiteNotSatisfiedException;
import domain.model.offer.exception.CoursesPassedPrerequisiteNotSatisfiedException;
import domain.model.register.exception.*;
import domain.model.register.exception.courseTakingException.*;
import shared.Entity;

import java.util.ArrayList;

public class AcademicRecord implements Entity<AcademicRecord> {
    private ArrayList<EnrolledCourse> enrolledCourses;
    private Term term;

    public AcademicRecord(Term term) {
        this.enrolledCourses = new ArrayList<>();
        this.term = term;
    }

    public boolean sameIdentityAs(AcademicRecord other) {
        return false; // todo: implement if needed
    }

    public ArrayList<Course> getPassedAndTakenCourses() {
        ArrayList<Course> passedReceivedCourses = new ArrayList<>();
        for(EnrolledCourse enrolledCourse : this.enrolledCourses)
            if(enrolledCourse.isPassed() || enrolledCourse.isTaken())
                passedReceivedCourses.add(enrolledCourse.getCourse());
        return passedReceivedCourses;
    }

    public void receiveCourse(CourseOffering courseOffering) {
        enrolledCourses.add(new EnrolledCourse(courseOffering));
    }

    public void deleteCourse(CourseOffering courseOffering) throws NotDeleteStudentCourseException {
        if (getCurrentNumberOfUnits() < 12)
            throw new NumberOfUnitsBelowMinimumException();
        this.enrolledCourses.remove(findReceivedCourse(courseOffering));
    }

    private int findReceivedCourse(CourseOffering courseOffering)
            throws CourseNotTakenException {
        for (int i = 0; i < this.enrolledCourses.size(); i++)
            if (this.enrolledCourses.get(i).getCourseOffering().sameIdentityAs(courseOffering))
                return i;
        throw new CourseNotTakenException();
    }

    private float getCurrentNumberOfUnits() {
        float numberOfUnit = 0;
        for (EnrolledCourse enrolledCourse : this.enrolledCourses)
            numberOfUnit += enrolledCourse.getTotalNumberOfUnits();
        return numberOfUnit;
    }

    public void validateConditions(CourseOffering courseOffering, float lastTermGpa)
            throws ConflictTimeException, DuplicateOfferingCourseTakenException,
            MaximumNumberOfUnitsException, ClassCapacityFullException,
            InternshipTakenWithOtherCoursesException {
        validateGpa(courseOffering, lastTermGpa);
        validateClassTimeConflict(courseOffering);
        validateExamTimeConflict(courseOffering);
        validateDuplicateCourse(courseOffering);
        validateCourseOfferingCapacity(courseOffering);
        validateInternshipConflict(courseOffering);
    }

    private void validateInternshipConflict(CourseOffering courseOffering)
            throws InternshipTakenWithOtherCoursesException {
        if (this.getNumOfUnits() > 0 && courseOffering.getCourseName().equals("internship")
            || this.enrolledCourses.get(0).getCourseName().equals("internship"))
            throw new InternshipTakenWithOtherCoursesException();
    }

    private void validateCourseOfferingCapacity(CourseOffering courseOffering)
            throws ClassCapacityFullException {
        courseOffering.validateCourseOfferingCapacity();
    }

    private void validateGpa(CourseOffering courseOffering, float lastTermGpa)
            throws MaximumNumberOfUnitsException {
        if (lastTermGpa < 12 && this.getNumOfUnits()+courseOffering.getTotalNumberOfUnits() > 14)
            throw new MaximumNumberOfUnitsDropoedStudentException();
        else if (lastTermGpa < 17 &&
                this.getNumOfUnits()+courseOffering.getTotalNumberOfUnits() > 20)
            throw new MaximumNumberOfUnitsUsualStudentException();
        else if (lastTermGpa >= 17 &&
                this.getNumOfUnits()+courseOffering.getTotalNumberOfUnits() > 24)
            throw new MaximumNumberOfUnitsTopStudentException();
    }

    private void validateDuplicateCourse(CourseOffering courseOffering)
            throws DuplicateOfferingCourseTakenException {
        for (EnrolledCourse enrolledCourse : this.enrolledCourses)
            enrolledCourse.validateDuplicateOfferingCourse(courseOffering);
    }

    private void validateClassTimeConflict(CourseOffering courseOffering)
            throws ConflictClassTimeException {
        for (EnrolledCourse enrolledCourse : this.enrolledCourses)
            enrolledCourse.validateClassTimeConflict(courseOffering);
    }

    private void validateExamTimeConflict(CourseOffering courseOffering)
            throws ConflictExamTimeException {
        for (EnrolledCourse enrolledCourse : this.enrolledCourses)
            enrolledCourse.validateExamTimeConflict(courseOffering);
    }

    private float getNumOfUnits() {
        float numOfUnits = 0;
        for (EnrolledCourse enrolledCourse : this.enrolledCourses)
            numOfUnits += enrolledCourse.getTotalNumberOfUnits();
        return numOfUnits;
    }

    private float getSumOfScores() {
        float sumOfScores = 0;
        for (EnrolledCourse enrolledCourse : this.enrolledCourses) {
            try {
                sumOfScores += enrolledCourse.getScore();
            } catch (NotGradedCourseException e) {
                System.out.println("This course not graded.");
            }
        }
        return sumOfScores;
    }

    public float getGpa() {
        return this.getSumOfScores() / this.getNumOfUnits();
    }

    public Term getTerm() {
        return term;
    }


    public void validatePassedPrerequisite(Course requiredPassedCourse)
            throws CoursesPassedPrerequisiteNotSatisfiedException {
        for (EnrolledCourse enrolledCourse: this.enrolledCourses)
            enrolledCourse.validatePassedPrerequisite(requiredPassedCourse);
    }

    public void validateCurrentPrerequisite(Course requiredCurrentCourse)
            throws CoursesNeededPrerequisiteNotSatisfiedException {
        for (EnrolledCourse enrolledCourse: this.enrolledCourses)
            enrolledCourse.validateCurrentPrerequisite(requiredCurrentCourse);
    }
}
