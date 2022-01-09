package domain.model.educationChart;

import domain.model.course.Course;
import domain.model.educationChart.exception.CourseNotFoundInThisTermException;
import domain.model.educationChart.exception.CourseNotSameWithChartTermItemException;
import shared.ValueObject;

import java.util.ArrayList;

public class ChartTerm implements ValueObject<ChartTerm> {
    private ArrayList<ChartTermItem> chartTermItems;

    public ChartTerm(ArrayList<ChartTermItem> chartTermItems) {
        this.chartTermItems = chartTermItems;
    }

    @Override
    public boolean sameValueAs(ChartTerm other) {
        return false; //todo: implement if necessary
    }

    public ArrayList<Course> getRequiredPassedCourse(Course offeredCourse)
            throws CourseNotFoundInThisTermException {
        for (ChartTermItem chartTermItem : this.chartTermItems) {
            try {
                return chartTermItem.getRequiredPassedCourse(offeredCourse);
            } catch (CourseNotSameWithChartTermItemException e) {
                e.printStackTrace();
            }
        }
        throw new CourseNotFoundInThisTermException();
    }

    public ArrayList<Course> getRequiredCurrentCourse(Course offeredCourse)
            throws CourseNotFoundInThisTermException {
        for (ChartTermItem chartTermItem: this.chartTermItems) {
            try {
                return chartTermItem.getRequiredCurrentCourse(offeredCourse);
            } catch (CourseNotSameWithChartTermItemException e) {
                e.printStackTrace();
            }
        }
        throw new CourseNotFoundInThisTermException();
    }
}
