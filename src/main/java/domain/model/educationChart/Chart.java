package domain.model.educationChart;

import domain.model.course.Course;
import domain.model.educationChart.exception.CourseNotFoundInStudentChartException;
import domain.model.educationChart.exception.CourseNotFoundInThisTermException;
import shared.ValueObject;

import java.util.ArrayList;

public class Chart implements ValueObject<Chart> {
    private int entranceYear;
    private ArrayList<ChartTerm> chartTerms;

    public Chart(int entranceYear, ArrayList<ChartTerm> chartTerms) {
        this.entranceYear = entranceYear;
        this.chartTerms = chartTerms;
    }

    @Override
    public boolean sameValueAs(Chart other) {
        return this.entranceYear == other.entranceYear &&
                this.chartTerms.equals(other.chartTerms);
    }

    public ArrayList<Course> getRequiredPassedCourses(Course offeredCourse)
            throws CourseNotFoundInStudentChartException {
        for (ChartTerm chartTerm: this.chartTerms) {
            try {
                return chartTerm.getRequiredPassedCourse(offeredCourse);
            } catch (CourseNotFoundInThisTermException e) {
                e.printStackTrace();
            }
        }
        throw new CourseNotFoundInStudentChartException();
    }

    public ArrayList<Course> getRequiredCurrentCourses(Course offeredCourse)
            throws CourseNotFoundInStudentChartException {
        for (ChartTerm chartTerm: this.chartTerms) {
            try {
                return chartTerm.getRequiredCurrentCourse(offeredCourse);
            } catch (CourseNotFoundInThisTermException e) {
                e.printStackTrace();
            }
        }
        throw new CourseNotFoundInStudentChartException();
    }
}
