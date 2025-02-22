# mock-util-calendar

A way to easily mock `java.util.Calendar.getInstance()`.

## Why?

Although the `Calendar` API is old and should be disregarded in favor of the Java8 Date/Time API, 
it is still widely used. It is omni-present in existing frameworks and even in new code. 

It is however not straightforward to statically mock the `Calendar.getInstance()` method call with 
Mockito because there is so much black magic and voodoo under the hood.

A simple `Mockito.mockStatic(...)` block is not enough to make it work.

## What?

A small re-usable utility class to easily run pieces of units of code in the context of a 
mocked calendar. 

Example:

*The following application logic is not easily (consistently) testable because the result will vary depending on when the test is run.*
```java
//Consider this method in a hypothetical service DayService 
public Date getDayBeforeYesterday() {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, -2);
    return calendar.getTime();
}
```

*You also don't want to write logic inside your test itself to work around the actual current date.*
*What you want (and need) is a way to easily test with fixed dates, or even run parameterized tests.*

*With `com.senn.mock.CalendarMocking`* this becomes easy:

```java
@Test
void testDayBeforeYesterday() {
    Date dayBeforeYesterday = CalendarMocking.withMockCalendar(
        2000, Calendar.JANUARY, 1,
        dayService::getDayBeforeYesterday
    );
    assertEquals(1999, dayBeforeYesterday.get(Calendar.YEAR));
    assertEquals(Calendar.DECEMBER, dayBeforeYesterday.get(Calendar.MONTH));
    assertEquals(30, dayBeforeYesterday.get(Calendar.DAY_OF_MONTH));
}
```

## Minimal versions

- Java 8
- Mockito 3.4.0

## Known issues

- only works for GregorianCalendar
- can only mock `Calendar.getInstance()` without method arguments