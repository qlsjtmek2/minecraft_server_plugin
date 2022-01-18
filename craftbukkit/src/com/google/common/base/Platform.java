// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.base;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible(emulated = true)
final class Platform
{
    private static final ThreadLocal<char[]> DEST_TL;
    
    static char[] charBufferFromThreadLocal() {
        return Platform.DEST_TL.get();
    }
    
    static long systemNanoTime() {
        return System.nanoTime();
    }
    
    static CharMatcher precomputeCharMatcher(final CharMatcher matcher) {
        return matcher.precomputedInternal();
    }
    
    static {
        DEST_TL = new ThreadLocal<char[]>() {
            protected char[] initialValue() {
                return new char[1024];
            }
        };
    }
}
