// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.io.IOException;
import java.io.StringReader;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.io.UnsupportedEncodingException;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.lang.reflect.Method;

public class StringUtils
{
    private static final int BYTE_RANGE = 256;
    private static byte[] allBytes;
    private static char[] byteToChars;
    private static Method toPlainStringMethod;
    static final int WILD_COMPARE_MATCH_NO_WILD = 0;
    static final int WILD_COMPARE_MATCH_WITH_WILD = 1;
    static final int WILD_COMPARE_NO_MATCH = -1;
    
    public static String consistentToString(final BigDecimal decimal) {
        if (decimal == null) {
            return null;
        }
        if (StringUtils.toPlainStringMethod != null) {
            try {
                return (String)StringUtils.toPlainStringMethod.invoke(decimal, (Object[])null);
            }
            catch (InvocationTargetException invokeEx) {}
            catch (IllegalAccessException ex) {}
        }
        return decimal.toString();
    }
    
    public static final String dumpAsHex(final byte[] byteBuffer, final int length) {
        final StringBuffer outputBuf = new StringBuffer(length * 4);
        int p = 0;
        for (int rows = length / 8, i = 0; i < rows && p < length; ++i) {
            int ptemp = p;
            for (int j = 0; j < 8; ++j) {
                String hexVal = Integer.toHexString(byteBuffer[ptemp] & 0xFF);
                if (hexVal.length() == 1) {
                    hexVal = "0" + hexVal;
                }
                outputBuf.append(hexVal + " ");
                ++ptemp;
            }
            outputBuf.append("    ");
            for (int j = 0; j < 8; ++j) {
                final int b = 0xFF & byteBuffer[p];
                if (b > 32 && b < 127) {
                    outputBuf.append((char)b + " ");
                }
                else {
                    outputBuf.append(". ");
                }
                ++p;
            }
            outputBuf.append("\n");
        }
        int n = 0;
        for (int k = p; k < length; ++k) {
            String hexVal2 = Integer.toHexString(byteBuffer[k] & 0xFF);
            if (hexVal2.length() == 1) {
                hexVal2 = "0" + hexVal2;
            }
            outputBuf.append(hexVal2 + " ");
            ++n;
        }
        for (int k = n; k < 8; ++k) {
            outputBuf.append("   ");
        }
        outputBuf.append("    ");
        for (int k = p; k < length; ++k) {
            final int b2 = 0xFF & byteBuffer[k];
            if (b2 > 32 && b2 < 127) {
                outputBuf.append((char)b2 + " ");
            }
            else {
                outputBuf.append(". ");
            }
        }
        outputBuf.append("\n");
        return outputBuf.toString();
    }
    
    private static boolean endsWith(final byte[] dataFrom, final String suffix) {
        for (int i = 1; i <= suffix.length(); ++i) {
            final int dfOffset = dataFrom.length - i;
            final int suffixOffset = suffix.length() - i;
            if (dataFrom[dfOffset] != suffix.charAt(suffixOffset)) {
                return false;
            }
        }
        return true;
    }
    
    public static byte[] escapeEasternUnicodeByteStream(final byte[] origBytes, final String origString, final int offset, final int length) {
        if (origBytes == null || origBytes.length == 0) {
            return origBytes;
        }
        final int bytesLen = origBytes.length;
        int bufIndex = 0;
        int strIndex = 0;
        final ByteArrayOutputStream bytesOut = new ByteArrayOutputStream(bytesLen);
        while (true) {
            if (origString.charAt(strIndex) == '\\') {
                bytesOut.write(origBytes[bufIndex++]);
            }
            else {
                int loByte = origBytes[bufIndex];
                if (loByte < 0) {
                    loByte += 256;
                }
                bytesOut.write(loByte);
                if (loByte >= 128) {
                    if (bufIndex < bytesLen - 1) {
                        int hiByte = origBytes[bufIndex + 1];
                        if (hiByte < 0) {
                            hiByte += 256;
                        }
                        bytesOut.write(hiByte);
                        ++bufIndex;
                        if (hiByte == 92) {
                            bytesOut.write(hiByte);
                        }
                    }
                }
                else if (loByte == 92 && bufIndex < bytesLen - 1) {
                    int hiByte = origBytes[bufIndex + 1];
                    if (hiByte < 0) {
                        hiByte += 256;
                    }
                    if (hiByte == 98) {
                        bytesOut.write(92);
                        bytesOut.write(98);
                        ++bufIndex;
                    }
                }
                ++bufIndex;
            }
            if (bufIndex >= bytesLen) {
                break;
            }
            ++strIndex;
        }
        return bytesOut.toByteArray();
    }
    
    public static char firstNonWsCharUc(final String searchIn) {
        return firstNonWsCharUc(searchIn, 0);
    }
    
    public static char firstNonWsCharUc(final String searchIn, final int startAt) {
        if (searchIn == null) {
            return '\0';
        }
        for (int length = searchIn.length(), i = startAt; i < length; ++i) {
            final char c = searchIn.charAt(i);
            if (!Character.isWhitespace(c)) {
                return Character.toUpperCase(c);
            }
        }
        return '\0';
    }
    
    public static char firstAlphaCharUc(final String searchIn, final int startAt) {
        if (searchIn == null) {
            return '\0';
        }
        for (int length = searchIn.length(), i = startAt; i < length; ++i) {
            final char c = searchIn.charAt(i);
            if (Character.isLetter(c)) {
                return Character.toUpperCase(c);
            }
        }
        return '\0';
    }
    
    public static final String fixDecimalExponent(String dString) {
        int ePos = dString.indexOf("E");
        if (ePos == -1) {
            ePos = dString.indexOf("e");
        }
        if (ePos != -1 && dString.length() > ePos + 1) {
            final char maybeMinusChar = dString.charAt(ePos + 1);
            if (maybeMinusChar != '-' && maybeMinusChar != '+') {
                final StringBuffer buf = new StringBuffer(dString.length() + 1);
                buf.append(dString.substring(0, ePos + 1));
                buf.append('+');
                buf.append(dString.substring(ePos + 1, dString.length()));
                dString = buf.toString();
            }
        }
        return dString;
    }
    
    public static final byte[] getBytes(final char[] c, final SingleByteCharsetConverter converter, final String encoding, final String serverEncoding, final boolean parserKnowsUnicode, final ExceptionInterceptor exceptionInterceptor) throws SQLException {
        try {
            byte[] b = null;
            if (converter != null) {
                b = converter.toBytes(c);
            }
            else if (encoding == null) {
                b = new String(c).getBytes();
            }
            else {
                final String s = new String(c);
                b = s.getBytes(encoding);
                if (!parserKnowsUnicode && (encoding.equalsIgnoreCase("SJIS") || encoding.equalsIgnoreCase("BIG5") || encoding.equalsIgnoreCase("GBK")) && !encoding.equalsIgnoreCase(serverEncoding)) {
                    b = escapeEasternUnicodeByteStream(b, s, 0, s.length());
                }
            }
            return b;
        }
        catch (UnsupportedEncodingException uee) {
            throw SQLError.createSQLException(Messages.getString("StringUtils.5") + encoding + Messages.getString("StringUtils.6"), "S1009", exceptionInterceptor);
        }
    }
    
    public static final byte[] getBytes(final char[] c, final SingleByteCharsetConverter converter, final String encoding, final String serverEncoding, final int offset, int length, final boolean parserKnowsUnicode, final ExceptionInterceptor exceptionInterceptor) throws SQLException {
        try {
            byte[] b = null;
            if (converter != null) {
                b = converter.toBytes(c, offset, length);
            }
            else if (encoding == null) {
                final byte[] temp = new String(c, offset, length).getBytes();
                length = temp.length;
                b = new byte[length];
                System.arraycopy(temp, 0, b, 0, length);
            }
            else {
                final String s = new String(c, offset, length);
                final byte[] temp2 = s.getBytes(encoding);
                length = temp2.length;
                b = new byte[length];
                System.arraycopy(temp2, 0, b, 0, length);
                if (!parserKnowsUnicode && (encoding.equalsIgnoreCase("SJIS") || encoding.equalsIgnoreCase("BIG5") || encoding.equalsIgnoreCase("GBK")) && !encoding.equalsIgnoreCase(serverEncoding)) {
                    b = escapeEasternUnicodeByteStream(b, s, offset, length);
                }
            }
            return b;
        }
        catch (UnsupportedEncodingException uee) {
            throw SQLError.createSQLException(Messages.getString("StringUtils.10") + encoding + Messages.getString("StringUtils.11"), "S1009", exceptionInterceptor);
        }
    }
    
    public static final byte[] getBytes(final char[] c, final String encoding, final String serverEncoding, final boolean parserKnowsUnicode, final MySQLConnection conn, final ExceptionInterceptor exceptionInterceptor) throws SQLException {
        try {
            SingleByteCharsetConverter converter = null;
            if (conn != null) {
                converter = conn.getCharsetConverter(encoding);
            }
            else {
                converter = SingleByteCharsetConverter.getInstance(encoding, null);
            }
            return getBytes(c, converter, encoding, serverEncoding, parserKnowsUnicode, exceptionInterceptor);
        }
        catch (UnsupportedEncodingException uee) {
            throw SQLError.createSQLException(Messages.getString("StringUtils.0") + encoding + Messages.getString("StringUtils.1"), "S1009", exceptionInterceptor);
        }
    }
    
    public static final byte[] getBytes(final String s, final SingleByteCharsetConverter converter, final String encoding, final String serverEncoding, final boolean parserKnowsUnicode, final ExceptionInterceptor exceptionInterceptor) throws SQLException {
        try {
            byte[] b = null;
            if (converter != null) {
                b = converter.toBytes(s);
            }
            else if (encoding == null) {
                b = s.getBytes();
            }
            else {
                b = s.getBytes(encoding);
                if (!parserKnowsUnicode && (encoding.equalsIgnoreCase("SJIS") || encoding.equalsIgnoreCase("BIG5") || encoding.equalsIgnoreCase("GBK")) && !encoding.equalsIgnoreCase(serverEncoding)) {
                    b = escapeEasternUnicodeByteStream(b, s, 0, s.length());
                }
            }
            return b;
        }
        catch (UnsupportedEncodingException uee) {
            throw SQLError.createSQLException(Messages.getString("StringUtils.5") + encoding + Messages.getString("StringUtils.6"), "S1009", exceptionInterceptor);
        }
    }
    
    public static final byte[] getBytesWrapped(final String s, final char beginWrap, final char endWrap, final SingleByteCharsetConverter converter, final String encoding, final String serverEncoding, final boolean parserKnowsUnicode, final ExceptionInterceptor exceptionInterceptor) throws SQLException {
        try {
            byte[] b = null;
            if (converter != null) {
                b = converter.toBytesWrapped(s, beginWrap, endWrap);
            }
            else if (encoding == null) {
                final StringBuffer buf = new StringBuffer(s.length() + 2);
                buf.append(beginWrap);
                buf.append(s);
                buf.append(endWrap);
                b = buf.toString().getBytes();
            }
            else {
                final StringBuffer buf = new StringBuffer(s.length() + 2);
                buf.append(beginWrap);
                buf.append(s);
                buf.append(endWrap);
                b = buf.toString().getBytes(encoding);
                if (!parserKnowsUnicode && (encoding.equalsIgnoreCase("SJIS") || encoding.equalsIgnoreCase("BIG5") || encoding.equalsIgnoreCase("GBK")) && !encoding.equalsIgnoreCase(serverEncoding)) {
                    b = escapeEasternUnicodeByteStream(b, s, 0, s.length());
                }
            }
            return b;
        }
        catch (UnsupportedEncodingException uee) {
            throw SQLError.createSQLException(Messages.getString("StringUtils.5") + encoding + Messages.getString("StringUtils.6"), "S1009", exceptionInterceptor);
        }
    }
    
    public static final byte[] getBytes(final String s, final SingleByteCharsetConverter converter, final String encoding, final String serverEncoding, final int offset, int length, final boolean parserKnowsUnicode, final ExceptionInterceptor exceptionInterceptor) throws SQLException {
        try {
            byte[] b = null;
            if (converter != null) {
                b = converter.toBytes(s, offset, length);
            }
            else if (encoding == null) {
                final byte[] temp = s.substring(offset, offset + length).getBytes();
                length = temp.length;
                b = new byte[length];
                System.arraycopy(temp, 0, b, 0, length);
            }
            else {
                final byte[] temp = s.substring(offset, offset + length).getBytes(encoding);
                length = temp.length;
                b = new byte[length];
                System.arraycopy(temp, 0, b, 0, length);
                if (!parserKnowsUnicode && (encoding.equalsIgnoreCase("SJIS") || encoding.equalsIgnoreCase("BIG5") || encoding.equalsIgnoreCase("GBK")) && !encoding.equalsIgnoreCase(serverEncoding)) {
                    b = escapeEasternUnicodeByteStream(b, s, offset, length);
                }
            }
            return b;
        }
        catch (UnsupportedEncodingException uee) {
            throw SQLError.createSQLException(Messages.getString("StringUtils.10") + encoding + Messages.getString("StringUtils.11"), "S1009", exceptionInterceptor);
        }
    }
    
    public static final byte[] getBytes(final String s, final String encoding, final String serverEncoding, final boolean parserKnowsUnicode, final MySQLConnection conn, final ExceptionInterceptor exceptionInterceptor) throws SQLException {
        try {
            SingleByteCharsetConverter converter = null;
            if (conn != null) {
                converter = conn.getCharsetConverter(encoding);
            }
            else {
                converter = SingleByteCharsetConverter.getInstance(encoding, null);
            }
            return getBytes(s, converter, encoding, serverEncoding, parserKnowsUnicode, exceptionInterceptor);
        }
        catch (UnsupportedEncodingException uee) {
            throw SQLError.createSQLException(Messages.getString("StringUtils.0") + encoding + Messages.getString("StringUtils.1"), "S1009", exceptionInterceptor);
        }
    }
    
    public static int getInt(final byte[] buf, final int offset, final int endPos) throws NumberFormatException {
        final int base = 10;
        int s;
        for (s = offset; Character.isWhitespace((char)buf[s]) && s < endPos; ++s) {}
        if (s == endPos) {
            throw new NumberFormatException(new String(buf));
        }
        boolean negative = false;
        if ((char)buf[s] == '-') {
            negative = true;
            ++s;
        }
        else if ((char)buf[s] == '+') {
            ++s;
        }
        final int save = s;
        final int cutoff = Integer.MAX_VALUE / base;
        int cutlim = Integer.MAX_VALUE % base;
        if (negative) {
            ++cutlim;
        }
        boolean overflow = false;
        int i = 0;
        while (s < endPos) {
            char c = (char)buf[s];
            if (Character.isDigit(c)) {
                c -= '0';
            }
            else {
                if (!Character.isLetter(c)) {
                    break;
                }
                c = (char)(Character.toUpperCase(c) - 'A' + '\n');
            }
            if (c >= base) {
                break;
            }
            if (i > cutoff || (i == cutoff && c > cutlim)) {
                overflow = true;
            }
            else {
                i *= base;
                i += c;
            }
            ++s;
        }
        if (s == save) {
            throw new NumberFormatException(new String(buf));
        }
        if (overflow) {
            throw new NumberFormatException(new String(buf));
        }
        return negative ? (-i) : i;
    }
    
    public static int getInt(final byte[] buf) throws NumberFormatException {
        return getInt(buf, 0, buf.length);
    }
    
    public static long getLong(final byte[] buf) throws NumberFormatException {
        return getLong(buf, 0, buf.length);
    }
    
    public static long getLong(final byte[] buf, final int offset, final int endpos) throws NumberFormatException {
        final int base = 10;
        int s;
        for (s = offset; Character.isWhitespace((char)buf[s]) && s < endpos; ++s) {}
        if (s == endpos) {
            throw new NumberFormatException(new String(buf));
        }
        boolean negative = false;
        if ((char)buf[s] == '-') {
            negative = true;
            ++s;
        }
        else if ((char)buf[s] == '+') {
            ++s;
        }
        final int save = s;
        final long cutoff = Long.MAX_VALUE / base;
        long cutlim = (int)(Long.MAX_VALUE % base);
        if (negative) {
            ++cutlim;
        }
        boolean overflow = false;
        long i = 0L;
        while (s < endpos) {
            char c = (char)buf[s];
            if (Character.isDigit(c)) {
                c -= '0';
            }
            else {
                if (!Character.isLetter(c)) {
                    break;
                }
                c = (char)(Character.toUpperCase(c) - 'A' + '\n');
            }
            if (c >= base) {
                break;
            }
            if (i > cutoff || (i == cutoff && c > cutlim)) {
                overflow = true;
            }
            else {
                i *= base;
                i += c;
            }
            ++s;
        }
        if (s == save) {
            throw new NumberFormatException(new String(buf));
        }
        if (overflow) {
            throw new NumberFormatException(new String(buf));
        }
        return negative ? (-i) : i;
    }
    
    public static short getShort(final byte[] buf) throws NumberFormatException {
        final short base = 10;
        int s;
        for (s = 0; Character.isWhitespace((char)buf[s]) && s < buf.length; ++s) {}
        if (s == buf.length) {
            throw new NumberFormatException(new String(buf));
        }
        boolean negative = false;
        if ((char)buf[s] == '-') {
            negative = true;
            ++s;
        }
        else if ((char)buf[s] == '+') {
            ++s;
        }
        final int save = s;
        final short cutoff = (short)(32767 / base);
        short cutlim = (short)(32767 % base);
        if (negative) {
            ++cutlim;
        }
        boolean overflow = false;
        short i = 0;
        while (s < buf.length) {
            char c = (char)buf[s];
            if (Character.isDigit(c)) {
                c -= '0';
            }
            else {
                if (!Character.isLetter(c)) {
                    break;
                }
                c = (char)(Character.toUpperCase(c) - 'A' + '\n');
            }
            if (c >= base) {
                break;
            }
            if (i > cutoff || (i == cutoff && c > cutlim)) {
                overflow = true;
            }
            else {
                i *= base;
                i += (short)c;
            }
            ++s;
        }
        if (s == save) {
            throw new NumberFormatException(new String(buf));
        }
        if (overflow) {
            throw new NumberFormatException(new String(buf));
        }
        return negative ? ((short)(-i)) : i;
    }
    
    public static final int indexOfIgnoreCase(final int startingPosition, final String searchIn, final String searchFor) {
        if (searchIn == null || searchFor == null || startingPosition > searchIn.length()) {
            return -1;
        }
        final int patternLength = searchFor.length();
        final int stringLength = searchIn.length();
        final int stopSearchingAt = stringLength - patternLength;
        if (patternLength == 0) {
            return -1;
        }
        final char firstCharOfPatternUc = Character.toUpperCase(searchFor.charAt(0));
        final char firstCharOfPatternLc = Character.toLowerCase(searchFor.charAt(0));
        for (int i = startingPosition; i <= stopSearchingAt; ++i) {
            if (isNotEqualIgnoreCharCase(searchIn, firstCharOfPatternUc, firstCharOfPatternLc, i)) {
                while (++i <= stopSearchingAt && isNotEqualIgnoreCharCase(searchIn, firstCharOfPatternUc, firstCharOfPatternLc, i)) {}
            }
            if (i <= stopSearchingAt) {
                int j = i + 1;
                final int end = j + patternLength - 1;
                for (int k = 1; j < end && (Character.toLowerCase(searchIn.charAt(j)) == Character.toLowerCase(searchFor.charAt(k)) || Character.toUpperCase(searchIn.charAt(j)) == Character.toUpperCase(searchFor.charAt(k))); ++j, ++k) {}
                if (j == end) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    private static final boolean isNotEqualIgnoreCharCase(final String searchIn, final char firstCharOfPatternUc, final char firstCharOfPatternLc, final int i) {
        return Character.toLowerCase(searchIn.charAt(i)) != firstCharOfPatternLc && Character.toUpperCase(searchIn.charAt(i)) != firstCharOfPatternUc;
    }
    
    public static final int indexOfIgnoreCase(final String searchIn, final String searchFor) {
        return indexOfIgnoreCase(0, searchIn, searchFor);
    }
    
    public static int indexOfIgnoreCaseRespectMarker(final int startAt, final String src, final String target, final String marker, final String markerCloses, final boolean allowBackslashEscapes) {
        char contextMarker = '\0';
        boolean escaped = false;
        int markerTypeFound = 0;
        final int srcLength = src.length();
        int ind = 0;
        for (int i = startAt; i < srcLength; ++i) {
            final char c = src.charAt(i);
            if (allowBackslashEscapes && c == '\\') {
                escaped = !escaped;
            }
            else if (contextMarker != '\0' && c == markerCloses.charAt(markerTypeFound) && !escaped) {
                contextMarker = '\0';
            }
            else if ((ind = marker.indexOf(c)) != -1 && !escaped && contextMarker == '\0') {
                markerTypeFound = ind;
                contextMarker = c;
            }
            else if ((Character.toUpperCase(c) == Character.toUpperCase(target.charAt(0)) || Character.toLowerCase(c) == Character.toLowerCase(target.charAt(0))) && !escaped && contextMarker == '\0' && startsWithIgnoreCase(src, i, target)) {
                return i;
            }
        }
        return -1;
    }
    
    public static int indexOfIgnoreCaseRespectQuotes(final int startAt, final String src, final String target, final char quoteChar, final boolean allowBackslashEscapes) {
        char contextMarker = '\0';
        boolean escaped = false;
        for (int srcLength = src.length(), i = startAt; i < srcLength; ++i) {
            final char c = src.charAt(i);
            if (allowBackslashEscapes && c == '\\') {
                escaped = !escaped;
            }
            else if (c == contextMarker && !escaped) {
                contextMarker = '\0';
            }
            else if (c == quoteChar && !escaped && contextMarker == '\0') {
                contextMarker = c;
            }
            else if ((Character.toUpperCase(c) == Character.toUpperCase(target.charAt(0)) || Character.toLowerCase(c) == Character.toLowerCase(target.charAt(0))) && !escaped && contextMarker == '\0' && startsWithIgnoreCase(src, i, target)) {
                return i;
            }
        }
        return -1;
    }
    
    public static final List split(final String stringToSplit, final String delimitter, final boolean trim) {
        if (stringToSplit == null) {
            return new ArrayList();
        }
        if (delimitter == null) {
            throw new IllegalArgumentException();
        }
        final StringTokenizer tokenizer = new StringTokenizer(stringToSplit, delimitter, false);
        final List splitTokens = new ArrayList(tokenizer.countTokens());
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (trim) {
                token = token.trim();
            }
            splitTokens.add(token);
        }
        return splitTokens;
    }
    
    public static final List<String> split(final String stringToSplit, final String delimiter, final String markers, final String markerCloses, final boolean trim) {
        if (stringToSplit == null) {
            return new ArrayList<String>();
        }
        if (delimiter == null) {
            throw new IllegalArgumentException();
        }
        int delimPos = 0;
        int currentPos = 0;
        final List<String> splitTokens = new ArrayList<String>();
        while ((delimPos = indexOfIgnoreCaseRespectMarker(currentPos, stringToSplit, delimiter, markers, markerCloses, false)) != -1) {
            String token = stringToSplit.substring(currentPos, delimPos);
            if (trim) {
                token = token.trim();
            }
            splitTokens.add(token);
            currentPos = delimPos + 1;
        }
        if (currentPos < stringToSplit.length()) {
            String token = stringToSplit.substring(currentPos);
            if (trim) {
                token = token.trim();
            }
            splitTokens.add(token);
        }
        return splitTokens;
    }
    
    private static boolean startsWith(final byte[] dataFrom, final String chars) {
        for (int i = 0; i < chars.length(); ++i) {
            if (dataFrom[i] != chars.charAt(i)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean startsWithIgnoreCase(final String searchIn, final int startAt, final String searchFor) {
        return searchIn.regionMatches(true, startAt, searchFor, 0, searchFor.length());
    }
    
    public static boolean startsWithIgnoreCase(final String searchIn, final String searchFor) {
        return startsWithIgnoreCase(searchIn, 0, searchFor);
    }
    
    public static boolean startsWithIgnoreCaseAndNonAlphaNumeric(final String searchIn, final String searchFor) {
        if (searchIn == null) {
            return searchFor == null;
        }
        int beginPos;
        int inLength;
        char c;
        for (beginPos = 0, inLength = searchIn.length(), beginPos = 0; beginPos < inLength; ++beginPos) {
            c = searchIn.charAt(beginPos);
            if (Character.isLetterOrDigit(c)) {
                break;
            }
        }
        return startsWithIgnoreCase(searchIn, beginPos, searchFor);
    }
    
    public static boolean startsWithIgnoreCaseAndWs(final String searchIn, final String searchFor) {
        return startsWithIgnoreCaseAndWs(searchIn, searchFor, 0);
    }
    
    public static boolean startsWithIgnoreCaseAndWs(final String searchIn, final String searchFor, int beginPos) {
        if (searchIn == null) {
            return searchFor == null;
        }
        for (int inLength = searchIn.length(); beginPos < inLength && Character.isWhitespace(searchIn.charAt(beginPos)); ++beginPos) {}
        return startsWithIgnoreCase(searchIn, beginPos, searchFor);
    }
    
    public static byte[] stripEnclosure(final byte[] source, final String prefix, final String suffix) {
        if (source.length >= prefix.length() + suffix.length() && startsWith(source, prefix) && endsWith(source, suffix)) {
            final int totalToStrip = prefix.length() + suffix.length();
            final int enclosedLength = source.length - totalToStrip;
            final byte[] enclosed = new byte[enclosedLength];
            final int startPos = prefix.length();
            final int numToCopy = enclosed.length;
            System.arraycopy(source, startPos, enclosed, 0, numToCopy);
            return enclosed;
        }
        return source;
    }
    
    public static final String toAsciiString(final byte[] buffer) {
        return toAsciiString(buffer, 0, buffer.length);
    }
    
    public static final String toAsciiString(final byte[] buffer, final int startPos, final int length) {
        final char[] charArray = new char[length];
        int readpoint = startPos;
        for (int i = 0; i < length; ++i) {
            charArray[i] = (char)buffer[readpoint];
            ++readpoint;
        }
        return new String(charArray);
    }
    
    public static int wildCompare(final String searchIn, final String searchForWildcard) {
        if (searchIn == null || searchForWildcard == null) {
            return -1;
        }
        if (searchForWildcard.equals("%")) {
            return 1;
        }
        int result = -1;
        final char wildcardMany = '%';
        final char wildcardOne = '_';
        final char wildcardEscape = '\\';
        int searchForPos = 0;
        final int searchForEnd = searchForWildcard.length();
        int searchInPos = 0;
        final int searchInEnd = searchIn.length();
    Label_0418:
        while (searchForPos != searchForEnd) {
            final char wildstrChar = searchForWildcard.charAt(searchForPos);
            while (searchForWildcard.charAt(searchForPos) != wildcardMany && wildstrChar != wildcardOne) {
                if (searchForWildcard.charAt(searchForPos) == wildcardEscape && searchForPos + 1 != searchForEnd) {
                    ++searchForPos;
                }
                if (searchInPos == searchInEnd || Character.toUpperCase(searchForWildcard.charAt(searchForPos++)) != Character.toUpperCase(searchIn.charAt(searchInPos++))) {
                    return 1;
                }
                if (searchForPos == searchForEnd) {
                    return (searchInPos != searchInEnd) ? 1 : 0;
                }
                result = 1;
            }
            Label_0223: {
                if (searchForWildcard.charAt(searchForPos) == wildcardOne) {
                    while (searchInPos != searchInEnd) {
                        ++searchInPos;
                        if (++searchForPos >= searchForEnd || searchForWildcard.charAt(searchForPos) != wildcardOne) {
                            if (searchForPos == searchForEnd) {
                                break Label_0418;
                            }
                            break Label_0223;
                        }
                    }
                    return result;
                }
            }
            if (searchForWildcard.charAt(searchForPos) == wildcardMany) {
                ++searchForPos;
                while (searchForPos != searchForEnd) {
                    if (searchForWildcard.charAt(searchForPos) != wildcardMany) {
                        if (searchForWildcard.charAt(searchForPos) != wildcardOne) {
                            break;
                        }
                        if (searchInPos == searchInEnd) {
                            return -1;
                        }
                        ++searchInPos;
                    }
                    ++searchForPos;
                }
                if (searchForPos == searchForEnd) {
                    return 0;
                }
                if (searchInPos == searchInEnd) {
                    return -1;
                }
                char cmp;
                if ((cmp = searchForWildcard.charAt(searchForPos)) == wildcardEscape && searchForPos + 1 != searchForEnd) {
                    cmp = searchForWildcard.charAt(++searchForPos);
                }
                ++searchForPos;
                while (true) {
                    if (searchInPos != searchInEnd && Character.toUpperCase(searchIn.charAt(searchInPos)) != Character.toUpperCase(cmp)) {
                        ++searchInPos;
                    }
                    else {
                        if (searchInPos++ == searchInEnd) {
                            return -1;
                        }
                        final int tmp = wildCompare(searchIn, searchForWildcard);
                        if (tmp <= 0) {
                            return tmp;
                        }
                        if (searchInPos == searchInEnd || searchForWildcard.charAt(0) == wildcardMany) {
                            return -1;
                        }
                        continue;
                    }
                }
            }
        }
        return (searchInPos != searchInEnd) ? 1 : 0;
    }
    
    static byte[] s2b(final String s, final MySQLConnection conn) throws SQLException {
        if (s == null) {
            return null;
        }
        if (conn != null && conn.getUseUnicode()) {
            try {
                final String encoding = conn.getEncoding();
                if (encoding == null) {
                    return s.getBytes();
                }
                final SingleByteCharsetConverter converter = conn.getCharsetConverter(encoding);
                if (converter != null) {
                    return converter.toBytes(s);
                }
                return s.getBytes(encoding);
            }
            catch (UnsupportedEncodingException E) {
                return s.getBytes();
            }
        }
        return s.getBytes();
    }
    
    public static int lastIndexOf(final byte[] s, final char c) {
        if (s == null) {
            return -1;
        }
        for (int i = s.length - 1; i >= 0; --i) {
            if (s[i] == c) {
                return i;
            }
        }
        return -1;
    }
    
    public static int indexOf(final byte[] s, final char c) {
        if (s == null) {
            return -1;
        }
        for (int length = s.length, i = 0; i < length; ++i) {
            if (s[i] == c) {
                return i;
            }
        }
        return -1;
    }
    
    public static boolean isNullOrEmpty(final String toTest) {
        return toTest == null || toTest.length() == 0;
    }
    
    public static String stripComments(final String src, final String stringOpens, final String stringCloses, final boolean slashStarComments, final boolean slashSlashComments, final boolean hashComments, final boolean dashDashComments) {
        if (src == null) {
            return null;
        }
        final StringBuffer buf = new StringBuffer(src.length());
        final StringReader sourceReader = new StringReader(src);
        int contextMarker = 0;
        final boolean escaped = false;
        int markerTypeFound = -1;
        int ind = 0;
        int currentChar = 0;
        try {
            while ((currentChar = sourceReader.read()) != -1) {
                if (markerTypeFound != -1 && currentChar == stringCloses.charAt(markerTypeFound) && !escaped) {
                    contextMarker = 0;
                    markerTypeFound = -1;
                }
                else if ((ind = stringOpens.indexOf(currentChar)) != -1 && !escaped && contextMarker == 0) {
                    markerTypeFound = ind;
                    contextMarker = currentChar;
                }
                if (contextMarker == 0 && currentChar == 47 && (slashSlashComments || slashStarComments)) {
                    currentChar = sourceReader.read();
                    if (currentChar == 42 && slashStarComments) {
                        for (int prevChar = 0; (currentChar = sourceReader.read()) != 47 || prevChar != 42; prevChar = currentChar) {
                            if (currentChar == 13) {
                                currentChar = sourceReader.read();
                                if (currentChar == 10) {
                                    currentChar = sourceReader.read();
                                }
                            }
                            else if (currentChar == 10) {
                                currentChar = sourceReader.read();
                            }
                            if (currentChar < 0) {
                                break;
                            }
                        }
                        continue;
                    }
                    if (currentChar == 47 && slashSlashComments) {
                        while ((currentChar = sourceReader.read()) != 10 && currentChar != 13 && currentChar >= 0) {}
                    }
                }
                else if (contextMarker == 0 && currentChar == 35 && hashComments) {
                    while ((currentChar = sourceReader.read()) != 10 && currentChar != 13 && currentChar >= 0) {}
                }
                else if (contextMarker == 0 && currentChar == 45 && dashDashComments) {
                    currentChar = sourceReader.read();
                    if (currentChar == -1 || currentChar != 45) {
                        buf.append('-');
                        if (currentChar != -1) {
                            buf.append(currentChar);
                            continue;
                        }
                        continue;
                    }
                    else {
                        while ((currentChar = sourceReader.read()) != 10 && currentChar != 13 && currentChar >= 0) {}
                    }
                }
                if (currentChar != -1) {
                    buf.append((char)currentChar);
                }
            }
        }
        catch (IOException ex) {}
        return buf.toString();
    }
    
    public static String sanitizeProcOrFuncName(final String src) {
        if (src == null || src == "%") {
            return null;
        }
        return src;
    }
    
    public static List splitDBdotName(final String src, final String cat, final String quotId, final boolean isNoBslashEscSet) {
        if (src == null || src == "%") {
            return new ArrayList();
        }
        String tmpCat = cat;
        int trueDotIndex = -1;
        if (!" ".equals(quotId)) {
            trueDotIndex = indexOfIgnoreCaseRespectQuotes(0, src, quotId + ".", quotId.charAt(0), !isNoBslashEscSet);
            if (trueDotIndex == -1) {
                trueDotIndex = indexOfIgnoreCaseRespectQuotes(0, src, ".", quotId.charAt(0), !isNoBslashEscSet);
            }
        }
        else {
            trueDotIndex = src.indexOf(".");
        }
        final List retTokens = new ArrayList(2);
        String retval;
        if (trueDotIndex != -1) {
            tmpCat = src.substring(0, trueDotIndex);
            if (startsWithIgnoreCaseAndWs(tmpCat, quotId) && tmpCat.trim().endsWith(quotId)) {
                tmpCat = tmpCat.substring(1, tmpCat.length() - 1);
            }
            retval = src.substring(trueDotIndex + 1);
            retval = new String(stripEnclosure(retval.getBytes(), quotId, quotId));
        }
        else {
            retval = new String(stripEnclosure(src.getBytes(), quotId, quotId));
        }
        retTokens.add(tmpCat);
        retTokens.add(retval);
        return retTokens;
    }
    
    public static final boolean isEmptyOrWhitespaceOnly(final String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        for (int length = str.length(), i = 0; i < length; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static String escapeQuote(String src, final String quotChar) {
        if (src == null) {
            return null;
        }
        src = new String(stripEnclosure(src.getBytes(), quotChar, quotChar));
        int lastNdx = src.indexOf(quotChar);
        String tmpSrc = src.substring(0, lastNdx);
        tmpSrc = tmpSrc + quotChar + quotChar;
        String tmpRest;
        for (tmpRest = src.substring(lastNdx + 1, src.length()), lastNdx = tmpRest.indexOf(quotChar); lastNdx > -1; lastNdx = tmpRest.indexOf(quotChar)) {
            tmpSrc += tmpRest.substring(0, lastNdx);
            tmpSrc = tmpSrc + quotChar + quotChar;
            tmpRest = tmpRest.substring(lastNdx + 1, tmpRest.length());
        }
        tmpSrc = (src = tmpSrc + tmpRest);
        return src;
    }
    
    static {
        StringUtils.allBytes = new byte[256];
        StringUtils.byteToChars = new char[256];
        for (int i = -128; i <= 127; ++i) {
            StringUtils.allBytes[i + 128] = (byte)i;
        }
        final String allBytesString = new String(StringUtils.allBytes, 0, 255);
        for (int allBytesStringLen = allBytesString.length(), j = 0; j < 255 && j < allBytesStringLen; ++j) {
            StringUtils.byteToChars[j] = allBytesString.charAt(j);
        }
        try {
            StringUtils.toPlainStringMethod = BigDecimal.class.getMethod("toPlainString", (Class<?>[])new Class[0]);
        }
        catch (NoSuchMethodException ex) {}
    }
}
