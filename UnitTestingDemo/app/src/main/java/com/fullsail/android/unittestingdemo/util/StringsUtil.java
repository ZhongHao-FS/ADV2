package com.fullsail.android.unittestingdemo.util;

public class StringsUtil {
    public static boolean isEmailValid(String email) {
        if(!email.contains("@") || !email.contains(".")) {
            return false;
        }

        // There is no . after the @ symbol.
        if(email.lastIndexOf(".") < email.indexOf("@")) {
            return false;
        }

        // There is more than one @ symbol present.
        if(email.indexOf("@") != email.lastIndexOf("@")) {
            return false;
        }

        // Email ends in a .
        if(email.lastIndexOf(".") == (email.length() - 1)) {
            return false;
        }

        // Split the email into two parts and check each half for validity.
        String[] parts = email.split("@");

        if(parts[0].trim().length() == 0) {
            return false;
        }

        // First part ends in a period.
        if(parts[0].charAt(parts[0].length()-1) == '.') {
            return false;
        }

        if(parts[0].startsWith(".")) {
            return false;
        }

        // Second part starts with a period.
        if(parts[1].startsWith(".")) {
            return false;
        }

        char[] chars = email.toCharArray();
        for(char c : chars){
            if(!Character.isLetter(c) && !Character.isDigit(c) && c != '@' && c != '.'){
                return false;
            }
        }

        return true;
    }
}
