package com.ifkusyoba.itam_harkan_pal.core.util;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class StringUtil {

    /**
     * Checks if a string is null or empty.
     *
     * @param str the string to check
     * @return true if the string is null or empty, false otherwise
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Capitalizes the first letter of a string.
     *
     * @param str the string to capitalize
     * @return the string with the first letter capitalized
     */
    public static String capitalizeFirstLetter(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * Joins a collection of strings with a specified delimiter.
     *
     * @param collection the collection of strings to join
     * @param delimiter  the delimiter to use
     * @return a single string with the collection elements joined by the delimiter
     */
    public static String join(Collection<String> collection, String delimiter) {
        if (collection == null || collection.isEmpty()) {
            return "";
        }
        return String.join(delimiter, collection);
    }

    /**
     * Reverses a string.
     *
     * @param str the string to reverse
     * @return the reversed string
     */
    public static String reverse(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        return new StringBuilder(str).reverse().toString();
    }

    /**
     * Converts a string to snake_case.
     *
     * @param str the string to convert
     * @return the snake_case version of the string
     */
    public static String toSnakeCase(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        return str
                .replaceAll("([a-z])([A-Z]+)", "$1_$2")
                .replaceAll("([A-Z])([A-Z][a-z])", "$1_$2")
                .toLowerCase();
    }

    /**
     * Converts a string to camelCase.
     *
     * @param str the string to convert
     * @return the camelCase version of the string
     */
    public static String toCamelCase(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        String[] parts = str.split("_");
        return Arrays.stream(parts)
                .map(part -> capitalizeFirstLetter(part.toLowerCase()))
                .collect(Collectors.joining());
    }

    /**
     * Truncates a string to a specified length, appending "..." if it exceeds the
     * length.
     *
     * @param str    the string to truncate
     * @param length the maximum length
     * @return the truncated string
     */
    public static String truncate(String str, int length) {
        if (isNullOrEmpty(str) || str.length() <= length) {
            return str;
        }
        return str.substring(0, length) + "...";
    }

    /**
     * Capitalizes all characters in a string.
     *
     * @param str the string to capitalize
     * @return the string with all characters capitalized
     */
    public static String capitalizeAll(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        return str.toUpperCase();
    }

    /**
     * Converts all characters in a string to lowercase.
     *
     * @param str the string to decapitalize
     * @return the string with all characters in lowercase
     */
    public static String decapitalizeAll(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        return str.toLowerCase();
    }

    public static String generateRandomString(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be greater than 0");
        }

        SecureRandom randomizer = new SecureRandom();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = randomizer.nextInt(characters.length());
            sb.append(characters.charAt(randomIndex));
        }

        return sb.toString();
    }
}
