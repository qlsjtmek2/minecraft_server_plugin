// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Iterator;
import java.util.Collection;
import javax.annotation.Nullable;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(emulated = true)
public final class ObjectArrays
{
    @GwtIncompatible("Array.newInstance(Class, int)")
    public static <T> T[] newArray(final Class<T> type, final int length) {
        return Platform.newArray(type, length);
    }
    
    public static <T> T[] newArray(final T[] reference, final int length) {
        return Platform.newArray(reference, length);
    }
    
    @GwtIncompatible("Array.newInstance(Class, int)")
    public static <T> T[] concat(final T[] first, final T[] second, final Class<T> type) {
        final T[] result = newArray(type, first.length + second.length);
        Platform.unsafeArrayCopy(first, 0, result, 0, first.length);
        Platform.unsafeArrayCopy(second, 0, result, first.length, second.length);
        return result;
    }
    
    public static <T> T[] concat(@Nullable final T element, final T[] array) {
        final T[] result = newArray(array, array.length + 1);
        result[0] = element;
        Platform.unsafeArrayCopy(array, 0, result, 1, array.length);
        return result;
    }
    
    public static <T> T[] concat(final T[] array, @Nullable final T element) {
        final T[] result = arraysCopyOf(array, array.length + 1);
        result[array.length] = element;
        return result;
    }
    
    static <T> T[] arraysCopyOf(final T[] original, final int newLength) {
        final T[] copy = (T[])newArray((Object[])original, newLength);
        Platform.unsafeArrayCopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }
    
    static <T> T[] toArrayImpl(final Collection<?> c, T[] array) {
        final int size = c.size();
        if (array.length < size) {
            array = newArray(array, size);
        }
        fillArray(c, array);
        if (array.length > size) {
            array[size] = null;
        }
        return array;
    }
    
    static Object[] toArrayImpl(final Collection<?> c) {
        return fillArray(c, new Object[c.size()]);
    }
    
    private static Object[] fillArray(final Iterable<?> elements, final Object[] array) {
        int i = 0;
        for (final Object element : elements) {
            array[i++] = element;
        }
        return array;
    }
    
    static void swap(final Object[] array, final int i, final int j) {
        final Object temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
