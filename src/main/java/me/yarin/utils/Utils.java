package me.yarin.utils;

import java.util.regex.Pattern;

public final class Utils {

    private Utils() {
    }

    /**
     * Methode om te kijken of het [email] een valid email is
     *
     * @param email het email address dat we proberen te valideren.
     * @return true als de email valid is.
     */
    public static boolean isEmailValid(final String email) {
        final String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        final Pattern pattern = Pattern.compile(emailRegex);
        return email != null && pattern.matcher(email).matches();
    }

    /**
     * Methode die kijkt of het [password] de requirements behaald.
     * De huidige requirements zijn 1 hoofdletter en minstens 8 tekens lang
     *
     * @param password het wachtwoord dat we proberen te valideren
     * @return true als het wachtwoord de requirements behaald
     */
    public static boolean doesPasswordMeetRequirements(final String password) {
        if (password == null) return false;
        int upperCase = 0;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                upperCase++;
            }
        }
        return upperCase >= 1 && password.length() >= 8;
    }

}
