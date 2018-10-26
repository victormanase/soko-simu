package com.sokosimu.sokosimu.SSHelpers;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    boolean isValid;

    public boolean isEmailValid(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(email);
        isValid = matcher.matches();
        return isValid;
    }
}
