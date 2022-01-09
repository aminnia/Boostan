package domain.model.utility;

import domain.model.register.exception.courseTakingException.ConflictClassTimeException;
import domain.model.register.exception.courseTakingException.ConflictExamTimeException;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;

public class Utility {
    public static void timeHasConflict(Time firstStartTime, Time firstEndTime,
                                       ArrayList<DayOfWeek> firstDaysOfWeek, Time secondStartTime,
                                       Time secondEndTime,
                                       ArrayList<DayOfWeek> secondDaysOfWeek)
            throws ConflictClassTimeException {

        if (daysConflict(firstDaysOfWeek, secondDaysOfWeek) &&
                hoursConflict(firstStartTime, firstEndTime, secondStartTime, secondEndTime))
            throw new ConflictClassTimeException();
    }

    public static void timeHasConflict(Time firstStartTime, Time firstEndTime,
                                       Date first, Time secondStartTime,
                                       Time secondEndTime,
                                       Date second) throws ConflictExamTimeException {
        if (first.equals(second) &&
                hoursConflict(firstStartTime, firstEndTime, secondStartTime, secondEndTime))
            throw new ConflictExamTimeException();
    }

    public static boolean daysConflict(ArrayList<DayOfWeek> firstDaysOfWeek,
                                       ArrayList<DayOfWeek> secondDaysOfWeek) {
        for (DayOfWeek dayOfWeek: firstDaysOfWeek) {
            boolean listContains = false;
            for (DayOfWeek dayOfWeek1 : secondDaysOfWeek)
                if (dayOfWeek.getValue() == dayOfWeek1.getValue())
                    listContains = true;
            if (!listContains)
                return false;
        }
        return true;
    }

    public static boolean hoursConflict(Time firstStartTime, Time firstEndTime,
                                        Time secondStartTime, Time secondEndTime) {
        return (firstStartTime.before(secondEndTime) && firstEndTime.after(secondStartTime));
    }
}
