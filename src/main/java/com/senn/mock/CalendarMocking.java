package com.senn.mock;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.function.Supplier;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.Mockito.mockStatic;

public final class CalendarMocking {

    public static <T> T withMockCalendar(int year, int month, int dayOfMonth, Supplier<T> supplier) {
        return withMockCalendar(year, month, dayOfMonth, 0, 0, 0, supplier);
    }

    public static <T> T withMockCalendar(int year, int month, int dayOfMonth, int hours, int minutes, int seconds, Supplier<T> supplier) {
        Calendar calendar = new GregorianCalendar(year, month, dayOfMonth, hours, minutes, seconds);
        try (MockedStatic<Calendar> mockCalendar = mockStatic(Calendar.class, Mockito.CALLS_REAL_METHODS)) {
            mockCalendar.when(Calendar::getInstance).thenReturn(calendar);
            return supplier.get();
        }
    }

}
