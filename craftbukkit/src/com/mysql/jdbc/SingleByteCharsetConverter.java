// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.util.HashMap;
import java.sql.SQLException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class SingleByteCharsetConverter
{
    private static final int BYTE_RANGE = 256;
    private static byte[] allBytes;
    private static final Map CONVERTER_MAP;
    private static final byte[] EMPTY_BYTE_ARRAY;
    private static byte[] unknownCharsMap;
    private char[] byteToChars;
    private byte[] charToByteMap;
    
    public static synchronized SingleByteCharsetConverter getInstance(final String encodingName, final Connection conn) throws UnsupportedEncodingException, SQLException {
        SingleByteCharsetConverter instance = SingleByteCharsetConverter.CONVERTER_MAP.get(encodingName);
        if (instance == null) {
            instance = initCharset(encodingName);
        }
        return instance;
    }
    
    public static SingleByteCharsetConverter initCharset(final String javaEncodingName) throws UnsupportedEncodingException, SQLException {
        if (CharsetMapping.isMultibyteCharset(javaEncodingName)) {
            return null;
        }
        final SingleByteCharsetConverter converter = new SingleByteCharsetConverter(javaEncodingName);
        SingleByteCharsetConverter.CONVERTER_MAP.put(javaEncodingName, converter);
        return converter;
    }
    
    public static String toStringDefaultEncoding(final byte[] buffer, final int startPos, final int length) {
        return new String(buffer, startPos, length);
    }
    
    private SingleByteCharsetConverter(final String encodingName) throws UnsupportedEncodingException {
        this.byteToChars = new char[256];
        this.charToByteMap = new byte[65536];
        final String allBytesString = new String(SingleByteCharsetConverter.allBytes, 0, 256, encodingName);
        final int allBytesLen = allBytesString.length();
        System.arraycopy(SingleByteCharsetConverter.unknownCharsMap, 0, this.charToByteMap, 0, this.charToByteMap.length);
        for (int i = 0; i < 256 && i < allBytesLen; ++i) {
            final char c = allBytesString.charAt(i);
            this.byteToChars[i] = c;
            this.charToByteMap[c] = SingleByteCharsetConverter.allBytes[i];
        }
    }
    
    public final byte[] toBytes(final char[] c) {
        if (c == null) {
            return null;
        }
        final int length = c.length;
        final byte[] bytes = new byte[length];
        for (int i = 0; i < length; ++i) {
            bytes[i] = this.charToByteMap[c[i]];
        }
        return bytes;
    }
    
    public final byte[] toBytesWrapped(final char[] c, final char beginWrap, final char endWrap) {
        if (c == null) {
            return null;
        }
        final int length = c.length + 2;
        final int charLength = c.length;
        final byte[] bytes = new byte[length];
        bytes[0] = this.charToByteMap[beginWrap];
        for (int i = 0; i < charLength; ++i) {
            bytes[i + 1] = this.charToByteMap[c[i]];
        }
        bytes[length - 1] = this.charToByteMap[endWrap];
        return bytes;
    }
    
    public final byte[] toBytes(final char[] chars, final int offset, final int length) {
        if (chars == null) {
            return null;
        }
        if (length == 0) {
            return SingleByteCharsetConverter.EMPTY_BYTE_ARRAY;
        }
        final byte[] bytes = new byte[length];
        for (int i = 0; i < length; ++i) {
            bytes[i] = this.charToByteMap[chars[i + offset]];
        }
        return bytes;
    }
    
    public final byte[] toBytes(final String s) {
        if (s == null) {
            return null;
        }
        final int length = s.length();
        final byte[] bytes = new byte[length];
        for (int i = 0; i < length; ++i) {
            bytes[i] = this.charToByteMap[s.charAt(i)];
        }
        return bytes;
    }
    
    public final byte[] toBytesWrapped(final String s, final char beginWrap, final char endWrap) {
        if (s == null) {
            return null;
        }
        final int stringLength = s.length();
        final int length = stringLength + 2;
        final byte[] bytes = new byte[length];
        bytes[0] = this.charToByteMap[beginWrap];
        for (int i = 0; i < stringLength; ++i) {
            bytes[i + 1] = this.charToByteMap[s.charAt(i)];
        }
        bytes[length - 1] = this.charToByteMap[endWrap];
        return bytes;
    }
    
    public final byte[] toBytes(final String s, final int offset, final int length) {
        if (s == null) {
            return null;
        }
        if (length == 0) {
            return SingleByteCharsetConverter.EMPTY_BYTE_ARRAY;
        }
        final byte[] bytes = new byte[length];
        for (int i = 0; i < length; ++i) {
            final char c = s.charAt(i + offset);
            bytes[i] = this.charToByteMap[c];
        }
        return bytes;
    }
    
    public final String toString(final byte[] buffer) {
        return this.toString(buffer, 0, buffer.length);
    }
    
    public final String toString(final byte[] buffer, final int startPos, final int length) {
        final char[] charArray = new char[length];
        int readpoint = startPos;
        for (int i = 0; i < length; ++i) {
            charArray[i] = this.byteToChars[buffer[readpoint] + 128];
            ++readpoint;
        }
        return new String(charArray);
    }
    
    static {
        SingleByteCharsetConverter.allBytes = new byte[256];
        CONVERTER_MAP = new HashMap();
        EMPTY_BYTE_ARRAY = new byte[0];
        SingleByteCharsetConverter.unknownCharsMap = new byte[65536];
        for (int i = -128; i <= 127; ++i) {
            SingleByteCharsetConverter.allBytes[i + 128] = (byte)i;
        }
        for (int i = 0; i < SingleByteCharsetConverter.unknownCharsMap.length; ++i) {
            SingleByteCharsetConverter.unknownCharsMap[i] = 63;
        }
    }
}
