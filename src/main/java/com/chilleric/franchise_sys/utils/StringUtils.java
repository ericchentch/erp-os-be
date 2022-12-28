package com.chilleric.franchise_sys.utils;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static String camelCaseToSnakeCase(String strInput) {
        Matcher m = Pattern.compile("(?<=[a-z])[A-Z]").matcher(strInput);
        String result = m.replaceAll(match -> "_" + match.group().toLowerCase());
        return result;
    }

    public static String camelCaseToText(String strInput) {
        Matcher m = Pattern.compile("(?<=[a-z])[A-Z]").matcher(strInput);
        String result = m.replaceAll(match -> " " + match.group().toLowerCase());
        return result;
    }

    public static String camelCaseToPascalCase(String strInput) {
        Matcher m = Pattern.compile("(?<=[a-z])[A-Z]").matcher(strInput);
        String result = m.replaceAll(match -> match.group().toUpperCase());
        return result;
    }

    public static String normalizeString(String strInput) {
        return Normalizer
                .normalize(strInput, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }
}
