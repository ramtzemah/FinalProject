package com.example.finalproject.Calculations;

public class Calculation {

    public static boolean isValidId(String id) {
        // Ensure that the ID is 9 digits long
//TODO fix IT
//        if (id.length() != 9) {
//            return false;
//        }
//
//        // Multiply each digit by a weight factor and sum the products
//        int sum = 0;
//        for (int i = 0; i < 9; i++) {
//            int digit = Integer.parseInt(id.substring(i, i+1));
//            int weight = (i % 2 == 0) ? 1 : 2;
//            int product = digit * weight;
//            if (product > 9) {
//                product -= 9;
//            }
//            sum += product;
//        }
//
//        // Check if the sum is divisible by 10
//        return (sum % 10 == 0);
    return true;
    }
}
