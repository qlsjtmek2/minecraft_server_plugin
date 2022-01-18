// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.util;

import java.net.URL;
import java.math.BigDecimal;

public class ValueUtil
{
    public static boolean areEqual(final Object obj1, final Object obj2) {
        if (obj1 == null) {
            return obj2 == null;
        }
        if (obj2 == null) {
            return false;
        }
        if (obj1 == obj2) {
            return true;
        }
        if (obj1 instanceof BigDecimal) {
            if (obj2 instanceof BigDecimal) {
                final Comparable<Object> com1 = (Comparable<Object>)obj1;
                return com1.compareTo(obj2) == 0;
            }
            return false;
        }
        else {
            if (obj1 instanceof URL) {
                return obj1.toString().equals(obj2.toString());
            }
            if (obj1 instanceof byte[] && obj2 instanceof byte[]) {
                return areEqualBytes((byte[])obj1, (byte[])obj2);
            }
            if (obj1 instanceof char[] && obj2 instanceof char[]) {
                return areEqualChars((char[])obj1, (char[])obj2);
            }
            return obj1.equals(obj2);
        }
    }
    
    private static boolean areEqualBytes(final byte[] b1, final byte[] b2) {
        if (b1.length != b2.length) {
            return false;
        }
        for (int i = 0; i < b1.length; ++i) {
            if (b1[i] != b2[i]) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean areEqualChars(final char[] b1, final char[] b2) {
        if (b1.length != b2.length) {
            return false;
        }
        for (int i = 0; i < b1.length; ++i) {
            if (b1[i] != b2[i]) {
                return false;
            }
        }
        return true;
    }
}
