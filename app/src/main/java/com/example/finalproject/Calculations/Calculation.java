package com.example.finalproject.Calculations;

public class Calculation {

    public static boolean isValidId(String idNumber) {
        // Remove any non-digit characters
        idNumber = idNumber.replaceAll("\\D", "");

        // Check if the ID number has 9 digits
        if (idNumber.length() != 9) {
            return false;
        }

        // Calculate the checksum
        int sum = 0;
        for (int i = 0; i < 8; i++) {
            int digit = Character.getNumericValue(idNumber.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 2 - (digit > 4 ? 9 : 0);
        }

        // Check if the checksum is valid
        int checksum = Character.getNumericValue(idNumber.charAt(8));
        return (sum + checksum) % 10 == 0;
    }
}
