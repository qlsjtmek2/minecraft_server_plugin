// 
// Decompiled by Procyon v0.5.30
// 

package org.fusesource.hawtjni.runtime;

public class PointerMath
{
    private static final boolean bits32;
    
    public static final long add(final long ptr, final long n) {
        if (PointerMath.bits32) {
            return (int)(ptr + n);
        }
        return ptr + n;
    }
    
    static {
        bits32 = (Library.getBitModel() == 32);
    }
}
