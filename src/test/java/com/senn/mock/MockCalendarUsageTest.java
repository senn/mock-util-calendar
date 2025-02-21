package com.senn.mock;

import java.util.Calendar;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.senn.mock.CalendarMocking.withMockCalendar;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MockCalendarUsageTest {

    private static final String FORMAT = "It was the %s day of %s of the year %s, and all was well with the world...";

    @ParameterizedTest
    @CsvSource({
            "1986,8,11,12,5,38",
            "2025,0,1,23,59,59",
            "2999,11,31,13,37,0"
    })
    void testMockCalendar_currentDay(final String year, final String month, final String day,
                                     final String hours, final String minutes, final String seconds) {
        Calendar result = withMockCalendar(
                toInt(year),
                toInt(month),
                toInt(day),
                toInt(hours),
                toInt(minutes),
                toInt(seconds),
                this::getCurrentDayCalendar);
        assertEquals(toInt(year), result.get(Calendar.YEAR));
        assertEquals(toInt(month), result.get(Calendar.MONTH));
        assertEquals(toInt(day), result.get(Calendar.DAY_OF_MONTH));
        assertEquals(toInt(hours), result.get(Calendar.HOUR_OF_DAY));
        assertEquals(toInt(minutes), result.get(Calendar.MINUTE));
        assertEquals(toInt(seconds), result.get(Calendar.SECOND));
    }

    @ParameterizedTest
    @CsvSource({
            "2011,11,7,6th,December,2021",
            "2014,5,4,3rd,June,2024",
            "1986,3,30,29th,April,1996",
            "2000,0,1,31st,December,2009",
    })
    void testMockCalendar_calendarDependentLogic(final String year, final String month, final String day,
                                                 final String dayWord, final String monthWord, final String yearWord) {
        String result = withMockCalendar(
                toInt(year),
                toInt(month),
                toInt(day),
                this::getHumanReadableDate10YearsMinus1DayInFuture);
        assertEquals(String.format(FORMAT, dayWord, monthWord, yearWord), result);
    }

    private static int toInt(final String input) {
        return Integer.parseInt(input);
    }

    //methods with logic to verify

    private Calendar getCurrentDayCalendar() {
        return Calendar.getInstance();
    }

    private String getHumanReadableDate10YearsMinus1DayInFuture() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 10);
        calendar.add(Calendar.DATE, -1);

        String day = getDayWord(calendar.get(Calendar.DAY_OF_MONTH));
        String month = getMonthWord(calendar.get(Calendar.MONTH));
        String year = calendar.get(Calendar.YEAR) + "";
        return String.format(FORMAT, day, month, year);
    }

    private static String getDayWord(int day) {
        switch (day) {
            case 1:
            case 31:
                return day + "st";
            case 2:
                return day + "nd";
            case 3:
                return day + "rd";
            default:
                return day + "th";
        }
    }

    private static String getMonthWord(int month) {
        switch (month) {
            case Calendar.JANUARY:
                return "January";
            case Calendar.FEBRUARY:
                return "February";
            case Calendar.MARCH:
                return "March";
            case Calendar.APRIL:
                return "April";
            case Calendar.MAY:
                return "May";
            case Calendar.JUNE:
                return "June";
            case Calendar.JULY:
                return "July";
            case Calendar.AUGUST:
                return "August";
            case Calendar.SEPTEMBER:
                return "September";
            case Calendar.OCTOBER:
                return "October";
            case Calendar.NOVEMBER:
                return "November";
            case Calendar.DECEMBER:
                return "December";
            default:
                throw new IllegalArgumentException("Unknown month!");
        }
    }

}
