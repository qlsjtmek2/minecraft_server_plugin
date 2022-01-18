// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.ant;

public class StringReplace
{
    public static String replace(final String source, final String match, final String replace) {
        return replaceString(source, match, replace, 30, 0, source.length());
    }
    
    private static String replaceString(final String source, final String match, final String replace, int additionalSize, final int startPos, final int endPos) {
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
}
