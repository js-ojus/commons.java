package com.ojuslabs.util;

/**
 * Provides a few simple string utility functions.
 */
public final class StringUtils
{
    /**
     * This class is not intended to be instantiated.
     */
    private StringUtils() {
        // Intentionally left blank.
    }

    /**
     * Removes all occurrences of all characters in the specified pattern from
     * the given string.
     * 
     * @param src
     *            The string to squeeze.
     * @param pattern
     *            Each character in this string is squeezed out of the given
     *            string.
     * @return The squeezed string.
     */
    public static String squeezeFully(String src, String pattern) {
        String dest = src;
        for (int i = 0; i < pattern.length(); i++) {
            dest = dest.replaceAll(pattern.substring(i, i + 1), "");
        }
        return dest;
    }

    /**
     * Removes all but one consecutive occurrences of all characters in the
     * specified pattern from the given string.
     * 
     * @param src
     *            The string to squeeze.
     * @param pattern
     *            Each character in this string is squeezed to one occurrence in
     *            the given string.
     * @return The squeezed string.
     */
    public static String squeeze(String src, String pattern) {
        String dest = src;
        for (int i = 0; i < pattern.length(); i++) {
            String pat = pattern.substring(i, i + 1);
            dest = dest.replaceAll(pat + "+", pat);
        }
        return dest;
    }
}
