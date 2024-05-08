package util.test;

import org.junit.Before;
import org.junit.Test;
import qy_notSubmit.PasswordValidator;

import static org.junit.Assert.assertEquals;

public class PasswordValidatorTest {
    private PasswordValidator passwordValidator;

    @Before
    public void setUp() {
        passwordValidator = new PasswordValidator();
    }

    @Test
    public void testLengthLessThanFive() {
        assertEquals(false, passwordValidator.validate("abc"));
    }

    @Test
    public void testLengthBetweenFiveAndNine() {
        assertEquals(true, passwordValidator.validate("abc12"));
    }

    @Test
    public void testLengthMoreThanNine() {
        assertEquals(false, passwordValidator.validate("abcdefghi"));
    }

    @Test
    public void testContainsOnlyLettersAndDigits() {
        assertEquals(true, passwordValidator.validate("abc123"));
    }

    @Test
    public void testContainsOtherCharacters() {
        assertEquals(false, passwordValidator.validate("abc@123"));
    }

    @Test
    public void testContainsBlankSpaces() {
        assertEquals(false, passwordValidator.validate("abc 123"));
    }

    @Test
    public void testContainsAtLeastOneLetterAndOneDigit() {
        assertEquals(true, passwordValidator.validate("abc123"));
    }

    @Test
    public void testContainsOnlyDigits() {
        assertEquals(false, passwordValidator.validate("123456"));
    }

    @Test
    public void testMinimumValidLength() {
        assertEquals(true, passwordValidator.validate("abc12"));
    }

    @Test
    public void testMaximumValidLength() {
        assertEquals(false, passwordValidator.validate("abcdefghi"));
    }
}
