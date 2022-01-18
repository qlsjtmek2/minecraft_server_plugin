// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import java.lang.reflect.Array;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(emulated = true)
class Platform
{
    static <T> T[] clone(final T[] array) {
        return array.clone();
    }
    
    static void unsafeArrayCopy(final Object[] src, final int srcPos, final Object[] dest, final int destPos, final int length) {
        System.arraycopy(src, srcPos, dest, destPos, length);
    }
    
    @GwtIncompatible("Array.newInstance(Class, int)")
    static <T> T[] newArray(final Class<T> type, final int length) {
        return (T[])Array.newInstance(type, length);
    }
    
    static <T> T[] newArray(final T[] reference, final int length) {
        final Class<?> type = reference.getClass().getComponentType();
        final T[] result = (T[])Array.newInstance(type, length);
        return result;
    }
    
    static MapMaker tryWeakKeys(final MapMaker mapMaker) {
        return mapMaker.weakKeys();
    }
}
