// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util.internal;

import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.reflect.Method;
import javassist.CtClass;
import javassist.ClassPool;
import io.netty.util.internal.logging.InternalLogger;

final class JavassistTypeParameterMatcherGenerator
{
    private static final InternalLogger logger;
    private static final ClassPool classPool;
    
    static TypeParameterMatcher generate(final Class<?> type) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        }
        return generate(type, classLoader);
    }
    
    static TypeParameterMatcher generate(final Class<?> type, final ClassLoader classLoader) {
        final String typeName = typeName(type);
        final String className = "io.netty.util.internal.__matchers__." + typeName + "Matcher";
        try {
            try {
                return (TypeParameterMatcher)Class.forName(className, true, classLoader).newInstance();
            }
            catch (Exception e2) {
                final CtClass c = JavassistTypeParameterMatcherGenerator.classPool.getAndRename(NoOpTypeParameterMatcher.class.getName(), className);
                c.setModifiers(c.getModifiers() | 0x10);
                c.getDeclaredMethod("match").setBody("{ return $1 instanceof " + typeName + "; }");
                final byte[] byteCode = c.toBytecode();
                c.detach();
                final Method method = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE);
                method.setAccessible(true);
                final Class<?> generated = (Class<?>)method.invoke(classLoader, className, byteCode, 0, byteCode.length);
                JavassistTypeParameterMatcherGenerator.logger.debug("Generated: {}", generated.getName());
                return (TypeParameterMatcher)generated.newInstance();
            }
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }
    
    private static String typeName(final Class<?> type) {
        if (type.isArray()) {
            return typeName(type.getComponentType()) + "[]";
        }
        return type.getName();
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(JavassistTypeParameterMatcherGenerator.class);
        classPool = new ClassPool(true);
    }
}
