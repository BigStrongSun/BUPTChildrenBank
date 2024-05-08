package util;

/**
 * The {@code PasswordValidator} class provides a method to validate a password based on specific rules.
 * A valid password must be:
 * - It must be between 5 to 9 characters in length.
 * - It contain only letters and digits(letters: a-z and A-Z, digits: 0-9)
 * - It must include at least one letter and one digit.
 *
 * @author Yuxinyue Qian
 */
public class PasswordValidator {
    /**
     * Validates a password based on predefined criteria.
     *
     * @param password the password string to validate.
     * @return {@code true} if the password is valid according to the rules, {@code false} otherwise.
     */
    public boolean validate(String password) {
        // Check length
        if (password.length() < 5 || password.length() > 9) {
            return false;
        }

        // Check characters
        if (!password.matches("[a-zA-Z0-9]+")) {
            return false;
        }

        // Check content
        boolean hasLetter = false;
        boolean hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }
        if (!hasLetter || !hasDigit) {
            return false;
        }

        return true;
    }
}

