package ru.graduation.voting.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalTime;

@UtilityClass
public class DateUtil {

    public static final String PATTERN = "yyyy-MM-dd";
    private static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);
    public static final LocalTime END_TIME_VOTE = LocalTime.of(11, 0);

    public static LocalDate startDayOrMin(LocalDate localDate) {
        return localDate != null ? localDate : MIN_DATE;
    }

    public static LocalDate endDayOrMax(LocalDate localDate) {
        return localDate != null ? localDate : MAX_DATE;
    }
}