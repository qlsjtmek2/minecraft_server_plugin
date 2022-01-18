// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.joptsimple.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.bukkit.craftbukkit.libs.joptsimple.ValueConverter;

public final class Reflection
{
    public static <V> ValueConverter<V> findConverter(final Class<V> clazz) {
        final ValueConverter<V> valueOf = (ValueConverter<V>)valueOfConverter((Class<Object>)clazz);
        if (valueOf != null) {
            return valueOf;
        }
        final ValueConverter<V> constructor = (ValueConverter<V>)constructorConverter((Class<Object>)clazz);
        if (constructor != null) {
            return constructor;
        }
        throw new IllegalArgumentException(clazz + " is not a value type");
    }
    
    private static <V> ValueConverter<V> valueOfConverter(final Class<V> clazz) {
        try {
            final Method valueOf = clazz.getDeclaredMethod("valueOf", String.class);
            if (!meetsConverterRequirements(valueOf, clazz)) {
                return null;
            }
            return new MethodInvokingValueConverter<V>(valueOf, clazz);
        }
        catch (NoSuchMethodException ignored) {
            return null;
        }
    }
    
    private static <V> ValueConverter<V> constructorConverter(final Class<V> clazz) {
        try {
            return new ConstructorInvokingValueConverter<V>(clazz.getConstructor(String.class));
        }
        catch (NoSuchMethodException ignored) {
            return null;
        }
    }
    
    public static <T> T instantiate(final Constructor<T> constructor, final Object... args) {
        try {
            return constructor.newInstance(args);
        }
        catch (Exception ex) {
            throw reflectionException(ex);
        }
    }
    
    public static Object invoke(final Method method, final Object... args) {
        try {
            return method.invoke(null, args);
        }
        catch (Exception ex) {
            throw reflectionException(ex);
        }
    }
    
    private static boolean meetsConverterRequirements(final Method method, final Class<?> expectedReturnType) {
        final int modifiers = method.getModifiers();
        return Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && expectedReturnType.equals(method.getReturnType());
    }
    
    private static RuntimeException reflectionException(final Exception ex) {
        if (ex instanceof IllegalArgumentException) {
            return new ReflectionException(ex);
        }
        if (ex instanceof InvocationTargetException) {
            return new ReflectionException(ex.getCause());
        }
        if (ex instanceof RuntimeException) {
            return (RuntimeException)ex;
        }
        return new ReflectionException(ex);
    }
    
    static {
        new Reflection();
    }
}
