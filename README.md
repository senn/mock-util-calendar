# mock-util-calendar

A way to easily mock `java.util.Calendar.getInstance()`.

## Why?

Although the `Calendar` API is old and should be disregarded in favor of the Java8 Date/Time API, 
it is still widely used. It is omni-present in existing frameworks and even in new code. 

It is however not straightforward to statically mock the `Calendar.getInstance()` method call 
because there is so much black magic and voodoo under the hood.

A simple *mockStatic* block is not enough to make it work.

## How?

A small re-usable utility class to easily run pieces of units of code in the context of a 
mocked calendar. 

## Known issues

- only works for GregorianCalendar
- can only mock `Calendar.getInstance()` without method arguments