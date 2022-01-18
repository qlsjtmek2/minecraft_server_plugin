// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.util;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class StringHelper
{
    private static final char SINGLE_QUOTE = '\'';
    private static final char DOUBLE_QUOTE = '\"';
    
    public static HashMap<String, String> parseNameQuotedValue(final String tag) throws RuntimeException {
        if (tag == null || tag.length() < 1) {
            return null;
        }
        if (tag.charAt(tag.length() - 1) == '=') {
            throw new RuntimeException("missing quoted value at the end of " + tag);
        }
        final HashMap<String, String> map = new HashMap<String, String>();
        return parseNameQuotedValue(map, tag, 0);
    }
    
    private static HashMap<String, String> parseNameQuotedValue(final HashMap<String, String> map, final String tag, final int pos) throws RuntimeException {
        final int equalsPos = tag.indexOf("=", pos);
        if (equalsPos <= -1) {
            return map;
        }
        final char firstQuote = tag.charAt(equalsPos + 1);
        if (firstQuote != '\'' && firstQuote != '\"') {
            throw new RuntimeException("missing begin quote at " + equalsPos + "[" + tag.charAt(equalsPos + 1) + "] in [" + tag + "]");
        }
        final int endQuotePos = tag.indexOf(firstQuote, equalsPos + 2);
        if (endQuotePos == -1) {
            throw new RuntimeException("missing end quote [" + firstQuote + "] after " + pos + " in [" + tag + "]");
        }
        String name = tag.substring(pos, equalsPos);
        final String value = tag.substring(equalsPos + 2, endQuotePos);
        name = trimFront(name, " ");
        if (name.indexOf(39) > -1 || name.indexOf(34) > -1) {
            throw new RuntimeException("attribute name contains a quote [" + name + "]");
        }
        map.put(name, value);
        return parseNameQuotedValue(map, tag, endQuotePos + 1);
    }
    
    public static int countOccurances(final String content, final String occurs) {
        return countOccurances(content, occurs, 0, 0);
    }
    
    private static int countOccurances(final String content, final String occurs, int pos, int countSoFar) {
        final int equalsPos = content.indexOf(occurs, pos);
        if (equalsPos > -1) {
            ++countSoFar;
            pos = equalsPos + occurs.length();
            return countOccurances(content, occurs, pos, countSoFar);
        }
        return countSoFar;
    }
    
    public static Map<String, String> delimitedToMap(String allNameValuePairs, final String listDelimiter, final String nameValueSeparator) {
        final HashMap<String, String> params = new HashMap<String, String>();
        if (allNameValuePairs == null || allNameValuePairs.length() == 0) {
            return params;
        }
        allNameValuePairs = trimFront(allNameValuePairs, listDelimiter);
        return getKeyValue(params, 0, allNameValuePairs, listDelimiter, nameValueSeparator);
    }
    
    public static String trimFront(final String source, final String trim) {
        if (source == null) {
            return null;
        }
        if (source.indexOf(trim) == 0) {
            return trimFront(source.substring(trim.length()), trim);
        }
        return source;
    }
    
    public static boolean isNull(final String value) {
        return value == null || value.trim().length() == 0;
    }
    
    private static HashMap<String, String> getKeyValue(final HashMap<String, String> map, int pos, final String allNameValuePairs, final String listDelimiter, final String nameValueSeparator) {
        if (pos >= allNameValuePairs.length()) {
            return map;
        }
        final int equalsPos = allNameValuePairs.indexOf(nameValueSeparator, pos);
        int delimPos = allNameValuePairs.indexOf(listDelimiter, pos);
        if (delimPos == -1) {
            delimPos = allNameValuePairs.length();
        }
        if (equalsPos == -1) {
            return map;
        }
        if (delimPos == equalsPos + 1) {
            return getKeyValue(map, delimPos + 1, allNameValuePairs, listDelimiter, nameValueSeparator);
        }
        if (equalsPos > delimPos) {
            String key = allNameValuePairs.substring(pos, delimPos);
            key = key.trim();
            if (key.length() > 0) {
                map.put(key, null);
            }
            return getKeyValue(map, delimPos + 1, allNameValuePairs, listDelimiter, nameValueSeparator);
        }
        String key = allNameValuePairs.substring(pos, equalsPos);
        if (delimPos > -1) {
            final String value = allNameValuePairs.substring(equalsPos + 1, delimPos);
            key = key.trim();
            map.put(key, value);
            pos = delimPos + 1;
            return getKeyValue(map, pos, allNameValuePairs, listDelimiter, nameValueSeparator);
        }
        return map;
    }
    
    public static String[] delimitedToArray(final String str, final String delimiter, final boolean keepEmpties) {
        final ArrayList<String> list = new ArrayList<String>();
        final int startPos = 0;
        delimiter(str, delimiter, keepEmpties, startPos, list);
        final String[] result = new String[list.size()];
        return list.toArray(result);
    }
    
    private static void delimiter(final String str, final String delimiter, final boolean keepEmpties, final int startPos, final ArrayList<String> list) {
        final int endPos = str.indexOf(delimiter, startPos);
        if (endPos == -1) {
            if (startPos <= str.length()) {
                final String lastValue = str.substring(startPos, str.length());
                if (keepEmpties || lastValue.length() != 0) {
                    list.add(lastValue);
                }
            }
            return;
        }
        final String value = str.substring(startPos, endPos);
        if (keepEmpties || value.length() != 0) {
            list.add(value);
        }
        delimiter(str, delimiter, keepEmpties, endPos + 1, list);
    }
    
    public static String getBoundedString(final String str, final String leftBound, final String rightBound) throws RuntimeException {
        if (str == null) {
            throw new RuntimeException("string to parse is null?");
        }
        int startPos = str.indexOf(leftBound);
        if (startPos <= -1) {
            return null;
        }
        startPos += leftBound.length();
        final int endPos = str.indexOf(rightBound, startPos);
        if (endPos == -1) {
            throw new RuntimeException("Can't find rightBound: " + rightBound);
        }
        return str.substring(startPos, endPos);
    }
    
    public static String setBoundedString(final String str, final String leftBound, final String rightBound, final String replaceString) {
        final int startPos = str.indexOf(leftBound);
        if (startPos <= -1) {
            return str;
        }
        final int endPos = str.indexOf(rightBound, startPos + leftBound.length());
        if (endPos > -1) {
            final String toReplace = str.substring(startPos, endPos + 1);
            return replaceString(str, toReplace, replaceString);
        }
        return str;
    }
    
    public static String replaceString(final String source, final String match, final String replace) {
        if (source == null) {
            return null;
        }
        if (replace == null) {
            return source;
        }
        if (match == null) {
            throw new NullPointerException("match is null?");
        }
        if (match.equals(replace)) {
            return source;
        }
        return replaceString(source, match, replace, 30, 0, source.length());
    }
    
    public static String replaceString(final String source, final String match, final String replace, int additionalSize, final int startPos, final int endPos) {
        if (source == null) {
            return source;
        }
        final char match2 = match.charAt(0);
        final int matchLength = match.length();
        if (matchLength == 1 && replace.length() == 1) {
            final char replace2 = replace.charAt(0);
            return source.replace(match2, replace2);
        }
        if (matchLength >= replace.length()) {
            additionalSize = 0;
        }
        final int sourceLength = source.length();
        final int lastMatch = endPos - matchLength;
        final StringBuilder sb = new StringBuilder(sourceLength + additionalSize);
        if (startPos > 0) {
            sb.append(source.substring(0, startPos));
        }
        for (int i = startPos; i < sourceLength; ++i) {
            final char sourceChar = source.charAt(i);
            if (i > lastMatch || sourceChar != match2) {
                sb.append(sourceChar);
            }
            else {
                boolean isMatch = true;
                int sourceMatchPos = i;
                for (int j = 1; j < matchLength; ++j) {
                    ++sourceMatchPos;
                    if (source.charAt(sourceMatchPos) != match.charAt(j)) {
                        isMatch = false;
                        break;
                    }
                }
                if (isMatch) {
                    i = i + matchLength - 1;
                    sb.append(replace);
                }
                else {
                    sb.append(sourceChar);
                }
            }
        }
        return sb.toString();
    }
    
    public static String replaceStringMulti(final String source, final String[] match, final String replace) {
        return replaceStringMulti(source, match, replace, 30, 0, source.length());
    }
    
    public static String replaceStringMulti(final String source, final String[] match, final String replace, final int additionalSize, final int startPos, final int endPos) {
        int shortestMatch = match[0].length();
        final char[] match2 = new char[match.length];
        for (int i = 0; i < match2.length; ++i) {
            match2[i] = match[i].charAt(0);
            if (match[i].length() < shortestMatch) {
                shortestMatch = match[i].length();
            }
        }
        final StringBuilder sb = new StringBuilder(source.length() + additionalSize);
        final int len = source.length();
        final int lastMatch = endPos - shortestMatch;
        if (startPos > 0) {
            sb.append(source.substring(0, startPos));
        }
        int matchCount = 0;
        for (int j = startPos; j < len; ++j) {
            final char sourceChar = source.charAt(j);
            if (j > lastMatch) {
                sb.append(sourceChar);
            }
            else {
                matchCount = 0;
                for (int k = 0; k < match2.length; ++k) {
                    if (matchCount == 0 && sourceChar == match2[k] && match[k].length() + j <= len) {
                        ++matchCount;
                        int l;
                        for (l = 1; l < match[k].length(); ++l) {
                            if (source.charAt(j + l) != match[k].charAt(l)) {
                                --matchCount;
                                break;
                            }
                        }
                        if (matchCount > 0) {
                            j = j + l - 1;
                            sb.append(replace);
                            break;
                        }
                    }
                }
                if (matchCount == 0) {
                    sb.append(sourceChar);
                }
            }
        }
        return sb.toString();
    }
    
    public static String removeChar(final String s, final char chr) {
        final StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); ++i) {
            final char c = s.charAt(i);
            if (c != chr) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    
    public static String removeChars(final String s, final char[] chr) {
        final StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); ++i) {
            final char c = s.charAt(i);
            if (!charMatch(c, chr)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    
    private static boolean charMatch(final int iChr, final char[] chr) {
        for (int i = 0; i < chr.length; ++i) {
            if (iChr == chr[i]) {
                return true;
            }
        }
        return false;
    }
}
