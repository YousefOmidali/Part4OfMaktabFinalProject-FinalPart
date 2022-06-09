package com.maktabProject.Part4MaktabFinalProject.Final.part.entity.authentication;

public class CheckPassword {
    public Boolean checkPasswordCondition(String password) {

        //total score of password
        int iPasswordScore = 0;

        if (password.length() >= 8)
            iPasswordScore += 1;

        //if it contains one digit, add 1 to total score
        if (password.matches("(?=.*[0-9]).*"))
            iPasswordScore += 1;

        //if it contains one lower case letter, add 1 to total score
        if (password.matches("(?=.*[a-z]).*"))
            iPasswordScore += 1;

        //if it contains one upper case letter, add 1 to total score
        if (password.matches("(?=.*[A-Z]).*"))
            iPasswordScore += 1;

        //if it contains one special character, add 1 to total score
        if (password.matches("(?=.*[~!@#$%^&*()_-]).*"))
            iPasswordScore += 1;

        if (iPasswordScore == 5)
            return true;
        else
            return false;

    }
}
