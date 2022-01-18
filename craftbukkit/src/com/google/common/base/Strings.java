// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.base;

import javax.annotation.Nullable;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
public final class Strings
{
    public static String nullToEmpty(@Nullable final String string) {
        return (string == null) ? "" : string;
    }
    
    @Nullable
    public static String emptyToNull(@Nullable final String string) {
        return isNullOrEmpty(string) ? null : string;
    }
    
    public static boolean isNullOrEmpty(@Nullable final String string) {
        return string == null || string.length() == 0;
    }
    
    public static String padStart(final String string, final int minLength, final char padChar) {
        Preconditions.checkNotNull(string);
        if (string.length() >= minLength) {
            return string;
        }
        final StringBuilder sb = new StringBuilder(minLength);
        for (int i = string.length(); i < minLength; ++i) {
            sb.append(padChar);
        }
        sb.append(string);
        return sb.toString();
    }
    
    public static String padEnd(final String string, final int minLength, final char padChar) {
        Preconditions.checkNotNull(string);
        if (string.length() >= minLength) {
            return string;
        }
        final StringBuilder sb = new StringBuilder(minLength);
        sb.append(string);
        for (int i = string.length(); i < minLength; ++i) {
            sb.append(padChar);
        }
        return sb.toString();
    }
    
    public static String repeat(final String string, final int count) {
        Preconditions.checkNotNull(string);
        Preconditions.checkArgument(count >= 0, "invalid count: %s", count);
        final StringBuilder builder = new StringBuilder(string.length() * count);
        for (int i = 0; i < count; ++i) {
            builder.append(string);
        }
        return builder.toString();
    }
}
