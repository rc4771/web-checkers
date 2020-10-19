package com.webcheckers.util;

/**
 * Checks to see if a name is valid or not
 *
 * @author Mike White
 */
public class NameValidator {

	/**
	 * Checks to see if the character is a letter
	 * @param ch The character to check
	 * @return True if it is a letter
	 */
	private static boolean isLetter(char ch) {
		return (ch <= 'z' && ch >= 'a') || (ch >= 'A' && ch <= 'Z');
	}

	/**
	 * Checks to see if the character is a digit
	 * @param ch The character to check
	 * @return True if it is a digit
	 */
	private static boolean isNumber(char ch) {
		return ch <= '9' && ch >= '0';
	}

	/**
	 * Checks to see if the character is a spcace
	 * @param ch The character to check
	 * @return true if it's a space
	 */
	private static boolean isSpace(char ch) {
		return ch == ' ';
	}

	/**
	 * Checks to see if the character is alphanumeric
	 * @param ch The character to check
	 * @return True if it is alphanumeric
	 */
	private static boolean isAlphaNumeric(char ch) {
		return isLetter(ch) || isNumber(ch) || isSpace(ch);
	}

	/**
	 * Checks to see if the string is alphanumeric
	 * @param s The string to check
	 * @return True if it is alphanumeric
	 */
	private static boolean isAlphaNumeric(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (!isAlphaNumeric(s.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Checks if a string is a valid username, checks to make sure it's not null, not empty, not blank, and only
	 * contains alphanumeric characters and spaces. the first letter should be a letter, and the name should be
	 * less than 13 characters
	 * @param username
	 *      The username string to test
	 * @return
	 *      A boolean for if the username is valid or not
	 */
	public static boolean isValidUsername(String username) {
		if (username == null) { // false for null usernames
			return false;
		} else if (username.isEmpty()) { // false for empty usernames
			return false;
		} else if (!isLetter(username.charAt(0))) { // false for non-alphanumeric usernames
			return false;
		} else if (username.isBlank()) { // false for blank usernames
			return false;
		} else if (username.length() > 12) { // false for length greater than 12
			return false;
		} else { // otherwise, true
			return isAlphaNumeric(username);
		}
	}
}
