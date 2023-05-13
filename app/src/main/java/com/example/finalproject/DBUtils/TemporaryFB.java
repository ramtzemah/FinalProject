package com.example.finalproject.DBUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TemporaryFB {
    private static int oldestAge = 102;
    private static int startVotingAge = 18;

    public static int getOldestAge() {
        return oldestAge;
    }
    public static Date dateOfEndVotingBeforeFormat() {
        Date currentDate = new Date();

        // Create a Calendar instance and set it to the current date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // Set the desired date
        calendar.set(Calendar.YEAR, 2021);
        calendar.set(Calendar.MONTH, Calendar.MAY);
        calendar.set(Calendar.DAY_OF_MONTH, 13);
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);

        // Get the date object from the modified calendar
        Date desiredDate = calendar.getTime();

        return desiredDate;
    }
    public static String dateOfEndVotingAfterFormat() {
        Date desiredDate = dateOfEndVotingBeforeFormat();

        // Format the desired date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDate = dateFormat.format(desiredDate);

        return formattedDate;
    }


    public static Date dateOfStartVotingBeforeFormat() {
        Date currentDate = new Date();

        // Create a Calendar instance and set it to the current date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // Set the desired date
        calendar.set(Calendar.YEAR, 2021);
        calendar.set(Calendar.MONTH, Calendar.MAY);
        calendar.set(Calendar.DAY_OF_MONTH, 15);
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);

        // Get the date object from the modified calendar
        Date desiredDate = calendar.getTime();

        return desiredDate;
    }
    public static String dateOfStartVotingAfterFormat() {
        Date desiredDate = dateOfStartVotingBeforeFormat();

        // Format the desired date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDate = dateFormat.format(desiredDate);

        return formattedDate;
    }

    public static int startVotingAge() {
        return startVotingAge;
    }
}
