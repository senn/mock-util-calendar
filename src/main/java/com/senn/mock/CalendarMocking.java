/*
Free to use, modify, share, extend.
Drop a comment, or even better: contribute on Github if this was useful.

But please just start using the Java8 Date/Time API instead of Calendar API ;-)

https://github.com/senn/mock-util-calendar
 */
package com.senn.mock;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.function.Supplier;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.Mockito.mockStatic;

/**
 * A way to easily mock <code>Calendar.getInstance()</code>.<br><br>
 * Just pass a date, and a supplier (=the code you want to test) and this class will handle it using a mocked calendar.
 * <br><br>
 * @author Bart Thierens (<a href="https://github.com/senn">Bart Thierens</a>)
 */
public final class CalendarMocking {

    /**
     * Runs logic in the context of a mocked calendar with the provided values (hours, minutes and seconds default to 0)
     * @param year year as int, i.e. 2025
     * @param month month as int, use Calendar constants because it's month - 1 in Calendar API (range: 0-11)
     * @param dayOfMonth day of the month as int (range: 1-31)
     * @param supplier the code that does a calculation based on <code>Calendar.getInstance()</code>
     * @return the return value of the supplier.get() in the mocked calendar context
     * @param <T> return type
     * @see #withMockCalendar(int, int, int, int, int, int, Supplier)
     */
    public static <T> T withMockCalendar(int year, int month, int dayOfMonth, Supplier<T> supplier) {
        return withMockCalendar(year, month, dayOfMonth, 0, 0, 0, supplier);
    }

    /**
     * Runs logic in the context of a mocked calendar with the provided values
     * @param year year as int, i.e. 2025
     * @param month month as int, use Calendar constants because it's month - 1 in Calendar API (range: 0-11)
     * @param dayOfMonth day of the month as int (range: 1-31)
     * @param hours hours as int (range: 0-23)
     * @param minutes minutes as int (range: 0-59)
     * @param seconds seconds as int (range: 0-59)
     * @param supplier the code that does a calculation based on <code>Calendar.getInstance()</code>
     * @return the return value of the supplier.get() in the mocked calendar context
     * @param <T> return type
     */
    public static <T> T withMockCalendar(int year, int month, int dayOfMonth, int hours, int minutes, int seconds, Supplier<T> supplier) {
        Calendar calendar = new GregorianCalendar(year, month, dayOfMonth, hours, minutes, seconds);
        try (MockedStatic<Calendar> mockCalendar = mockStatic(Calendar.class, Mockito.CALLS_REAL_METHODS)) {
            mockCalendar.when(Calendar::getInstance).thenReturn(calendar);
            return supplier.get();
        }
    }

}
