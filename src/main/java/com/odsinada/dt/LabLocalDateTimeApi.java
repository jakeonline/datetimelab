package com.odsinada.dt;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

public class LabLocalDateTimeApi {

    public static void main(String[] args) {
        LabLocalDateTimeApi api = new LabLocalDateTimeApi();
        api.getLocalCurrentDateTime();
//        api.getZonedCurrentDateTime();

//        api.getModifiedDateTime();
//        api.getDefinedDateTime();
//        api.getFormattedDateTime();
//
//        api.showDateArithmetic();
//        api.showTimeArithmetic();
//        api.showTimeArithmeticUseCase();
//
//        api.showDateIntervalWithUseCase();
//        api.showTimeInterval();
//        api.showAdjusters();

//        api.showBackwardsCompatibility();
    }

    private void showBackwardsCompatibility() {

        Date currentDate = new Date();
        System.out.println("Java Date: " + currentDate);

        Instant now = currentDate.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(now, ZoneId.systemDefault());
        System.out.println("Converted to LocalDateTime: " + localDateTime);

        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(now, ZoneId.systemDefault());
        System.out.println("Converted to ZonedDateTime: " + zonedDateTime);

    }

    private void showAdjusters() {
        LocalDate currentDate = LocalDate.now();
        System.out.println("Current Day: " + currentDate + " (" + currentDate.getDayOfWeek() + ")");

        LocalDate nextMonday
                = currentDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        System.out.println("Next Monday: " + nextMonday);

        LocalDate sameDayNextWeek
                = currentDate.with(TemporalAdjusters.next(currentDate.getDayOfWeek()));
        System.out.println("Same Day today next week: " + sameDayNextWeek);

        System.out.println();

        LocalDate firstDayOfMonth = LocalDate.of(2017, 1, 1);
        System.out.println("Month start: " + firstDayOfMonth + " (" + firstDayOfMonth.getDayOfWeek() + ")");

        LocalDate firstSaturday
                = firstDayOfMonth.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
        System.out.println("First Saturday: " + firstSaturday);

        LocalDate secondSaturday
                = firstDayOfMonth.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY))
                    .with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        System.out.println("Second Saturday: " + secondSaturday);

    }

    private void showTimeInterval() {

        LocalTime currentTime = LocalTime.now();
        System.out.println("Current time: " + currentTime);

        LocalTime timeSixHoursLater = currentTime.plusHours(6);
        System.out.println("6 Hours later: " + timeSixHoursLater);

        LocalTime timeTwelveHoursLater = currentTime.plus(12, ChronoUnit.HOURS);
        System.out.println("12 Hours later: " + timeTwelveHoursLater);

        System.out.println();

        Duration durationA = Duration.between(currentTime, timeSixHoursLater.plusMinutes(1));
        System.out.println("Between " + currentTime.getHour()
                + " and " + timeSixHoursLater.getHour() + ": " + durationA);
        System.out.println("Between " + currentTime.getHour()
                + " and " + timeSixHoursLater.getHour() + ": " + durationA.toHours() + " hours");
        System.out.println("Between " + currentTime.getHour()
                + " and " + timeSixHoursLater.getHour() + ": " + durationA.toMinutes() + " minutes");
        System.out.println();

        Duration durationB = Duration.between(currentTime, timeTwelveHoursLater);
        System.out.println("Between " + currentTime.getHour()
                + " and " + timeTwelveHoursLater.getHour() + ": " + durationB);


    }

    private void showDateIntervalWithUseCase() {

        LocalDate startDate = LocalDate.of(2017, Month.JANUARY, 1);
        System.out.println("Start date: " + startDate + " (" + startDate.getMonth() + ")");

        LocalDate dateAfterOneMonth = startDate.plusMonths(1);
        System.out.println("After 1 month: " + dateAfterOneMonth
                + " (" + dateAfterOneMonth.getMonth() + ")");

        LocalDate dateAfterSixMonths = startDate.plus(6, ChronoUnit.MONTHS);
        System.out.println("After 6 months: " + dateAfterSixMonths
                + " (" + dateAfterSixMonths.getMonth() + ")");

        System.out.println();

        Period periodFuture = Period.between(startDate, dateAfterOneMonth);
        System.out.println("Between " + startDate.getMonth()
                + " and " + dateAfterOneMonth.getMonth() + ": " + periodFuture);

        Period periodPast = Period.between(dateAfterOneMonth, startDate);
        System.out.println("Between " + dateAfterOneMonth.getMonth()
                + " and " + startDate.getMonth() + ": " + periodPast);

        Period periodLaterFuture = Period.between(startDate, dateAfterSixMonths);
        System.out.println("Between " + startDate.getMonth()
                + " and " + dateAfterSixMonths.getMonth() + ": " + periodLaterFuture);


        System.out.println();

        // USE CASE: Find if date is in unavailability window

        boolean isUnavailableIfWithinDateRange
                = dateAfterOneMonth.isAfter(startDate)
                && dateAfterOneMonth.isBefore(dateAfterSixMonths);

        System.out.println("USE CASE - Is " + dateAfterOneMonth.getMonth()
                + " between " + startDate.getMonth()
                + " and " + dateAfterSixMonths.getMonth() + "?: "
                + isUnavailableIfWithinDateRange);

    }

    private void showDateArithmetic() {

        LocalDateTime currentDateTime = LocalDateTime.now();

        LocalDate currentDate = currentDateTime.toLocalDate();

        LocalDate dateWeekAfter = currentDate.plus(1, ChronoUnit.WEEKS);

        LocalDate dateMonthAfter = currentDate.plusMonths(1);

        LocalDate dateCenturyBefore = currentDate.minus(1, ChronoUnit.CENTURIES);

        System.out.println("Current date: " + currentDate);
        System.out.println("Week after: " + dateWeekAfter);
        System.out.println("Month after: " + dateMonthAfter);
        System.out.println("Century before: " + dateCenturyBefore);
    }

    private void showTimeArithmetic() {
        // using Date
        int TWELVE_HOURS_IN_MILLI_SECTIONS = 12 * 60 * 60 * 1000;
        Date timeDate = new Date((new Date()).getTime()
                - TWELVE_HOURS_IN_MILLI_SECTIONS);
        System.out.println("Date: " + timeDate);

        // using Calendar
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.add(Calendar.HOUR, -12);
        System.out.println("Calendar: " + timeCalendar.getTime());

        // using LocalTime
        LocalTime timeLocalTime = LocalTime.now().minus(12, ChronoUnit.HOURS);
        System.out.println("LocalTime: " + timeLocalTime);
    }

    private void showTimeArithmeticUseCase() {

        // USE CASE: Treat user session as expired
        // when he has not logged in in the past 12 hours

        LocalDateTime lastLoginTime = LocalDateTime.now().minusHours(15);
        System.out.println("Last login datetime: " + lastLoginTime);

        LocalDateTime pastTwelveHours = LocalDateTime.now().minusHours(12);

        boolean isExpired = lastLoginTime.isBefore(pastTwelveHours);
        System.out.println("USE CASE - Has not logged in in the past 12 hours?: " + isExpired);

    }


    private void getZonedCurrentDateTime() {

        System.out.println("Default Zone: " + ZoneId.systemDefault());
        ZonedDateTime currentDate = ZonedDateTime.now();
        System.out.println("Current Default datetime: " + currentDate);

        ZonedDateTime sydDateTime = ZonedDateTime.now(ZoneId.of("Australia/Sydney"));
        System.out.println("Current Sydney datetime: " + sydDateTime);

    }


    private void getLocalCurrentDateTime() {
        // Creating object representing current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println("Current datetime: " + currentDateTime);


        // Pick the date section
        LocalDate currentDate = currentDateTime.toLocalDate();
        System.out.println("Date: " + currentDate);


        // Pick the time section
        LocalTime currentTime = currentDateTime.toLocalTime();
        System.out.println("Time: " + currentTime);


        // Pick the details of the date section
        Month currentMonth = currentDateTime.getMonth();
        int day = currentDateTime.getDayOfMonth();
        int seconds = currentDateTime.getSecond();

        System.out.print("Month: " + currentMonth);
        System.out.print(" ... Day of month: " + day);
        System.out.print(" ... Seconds: " + seconds);
        System.out.println();
    }

    private void getModifiedDateTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println("Current datetime: " + currentDateTime);

        // Change details of current date i.e. same day of month now in January 2012
        LocalDate changedDate = currentDateTime.toLocalDate().withMonth(1).withYear(2012);
        System.out.println("Changed date: " + changedDate);


        // Change details of current time i.e. top of the hour
        LocalTime changedTime = currentDateTime.toLocalTime()
                .withMinute(0).withSecond(0).withNano(0);
        System.out.println("Changed time: " + changedTime);
    }

    private void getDefinedDateTime() {
        // Creation of defined date
        LocalDate definedDate = LocalDate.of(2015, Month.SEPTEMBER, 22);
        System.out.println("Defined date: " + definedDate);


        // Creation of defined time
        LocalTime definedTime = LocalTime.of(17, 30);
        System.out.println("Defined time: " + definedTime);

    }

    private void getFormattedDateTime() {
        DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern("yyyy MMMM dd");

        // Parsing date string
        LocalDate parsedDate = LocalDate.parse("1989-01-12");
        System.out.println("Parsed date: " + parsedDate);

        LocalDate parsedFormattedDate
                = LocalDate.parse("2000 October 01", formatter);
        System.out.println("Parsed custom date format: " + parsedFormattedDate);

        // Parsing time string
        LocalTime parsedTime = LocalTime.parse("21:15:11");
        System.out.println("Parsed time: " + parsedTime);

        LocalDateTime currentDate
                = LocalDateTime.now(ZoneId.of("Australia/Sydney"));

        System.out.println("Current date: " + currentDate);

        System.out.println("ISO_LOCAL_DATE_TIME: " + currentDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        System.out.println("Year/Full Month/Date: " + currentDate.format(formatter));


    }


}
