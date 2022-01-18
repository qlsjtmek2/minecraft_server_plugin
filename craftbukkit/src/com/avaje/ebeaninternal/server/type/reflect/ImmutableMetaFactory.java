// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type.reflect;

import java.util.HashSet;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Iterator;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.util.logging.Level;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ImmutableMetaFactory
{
    private static final Logger logger;
    
    public ImmutableMeta createImmutableMeta(final Class<?> cls) {
        final ScoreConstructor[] scoreConstructors = this.scoreConstructors(cls);
        final ArrayList<RuntimeException> errors = new ArrayList<RuntimeException>();
        int i = 0;
        while (i < scoreConstructors.length) {
            final Constructor<?> constructor = scoreConstructors[i].constructor;
            try {
                final Method[] getters = this.findGetters(cls, constructor);
                return new ImmutableMeta(constructor, getters);
            }
            catch (NoSuchMethodException e) {
                final String msg = "Error finding getter method on " + cls + " with constructor " + constructor;
                errors.add(new RuntimeException(msg, e));
                ++i;
                continue;
            }
            break;
        }
        String msg2 = "Was unable to use reflection to find a constructor and appropriate getters forimmutable type " + cls + ".  The errors while looking for the getter methods follow:";
        ImmutableMetaFactory.logger.severe(msg2);
        for (final RuntimeException runtimeException : errors) {
            ImmutableMetaFactory.logger.log(Level.SEVERE, "Error with " + cls, runtimeException);
        }
        msg2 = "Unable to use reflection to build ImmutableMeta for " + cls + ".  Associated Errors trying to find a constructor and getter methods have been logged";
        throw new RuntimeException(msg2);
    }
    
    private ScoreConstructor getScore(final Constructor<?> c) {
        final Class<?>[] parameterTypes = c.getParameterTypes();
        int score = -1000 * parameterTypes.length;
        for (int i = 0; i < parameterTypes.length; ++i) {
            if (parameterTypes[i].equals(String.class)) {
                ++score;
            }
            else if (parameterTypes[i].equals(BigDecimal.class)) {
                score -= 10;
            }
            else if (parameterTypes[i].equals(Timestamp.class)) {
                score -= 10;
            }
            else if (parameterTypes[i].equals(Double.TYPE)) {
                score -= 9;
            }
            else if (parameterTypes[i].equals(Double.class)) {
                score -= 8;
            }
            else if (parameterTypes[i].equals(Float.TYPE)) {
                score -= 7;
            }
            else if (parameterTypes[i].equals(Float.class)) {
                score -= 6;
            }
            else if (parameterTypes[i].equals(Long.TYPE)) {
                score -= 5;
            }
            else if (parameterTypes[i].equals(Long.class)) {
                score -= 4;
            }
            else if (parameterTypes[i].equals(Integer.TYPE)) {
                score -= 3;
            }
            else if (parameterTypes[i].equals(Integer.class)) {
                score -= 2;
            }
        }
        return new ScoreConstructor(score, (Constructor)c);
    }
    
    private ScoreConstructor[] scoreConstructors(final Class<?> cls) {
        int maxParamCount = 0;
        final Constructor<?>[] constructors = cls.getConstructors();
        ScoreConstructor[] score = new ScoreConstructor[constructors.length];
        for (int i = 0; i < constructors.length; ++i) {
            score[i] = this.getScore(constructors[i]);
            if (score[i].hasDuplicateParamTypes()) {
                final String msg = "Duplicate parameter types in " + score[i].constructor;
                throw new IllegalStateException(msg);
            }
            if (score[i].getParamCount() > maxParamCount) {
                maxParamCount = score[i].getParamCount();
            }
        }
        final ArrayList<ScoreConstructor> list = new ArrayList<ScoreConstructor>();
        for (int j = 0; j < score.length; ++j) {
            if (score[j].getParamCount() == maxParamCount) {
                list.add(score[j]);
            }
        }
        score = list.toArray(new ScoreConstructor[list.size()]);
        Arrays.sort(score);
        return score;
    }
    
    private Method[] findGetters(final Class<?> cls, final Constructor<?> c) throws NoSuchMethodException {
        final Method[] methods = cls.getMethods();
        final Class<?>[] paramTypes = c.getParameterTypes();
        final Method[] readers = new Method[paramTypes.length];
        for (int i = 0; i < paramTypes.length; ++i) {
            Method getter = this.findGetter(paramTypes[i], methods);
            if (getter == null && paramTypes.length == 1 && paramTypes[i].equals(String.class)) {
                getter = this.findToString(cls);
            }
            if (getter == null) {
                throw new NoSuchMethodException("Get Method not found for " + paramTypes[i] + " in " + cls);
            }
            readers[i] = getter;
        }
        return readers;
    }
    
    private Method findToString(final Class<?> cls) throws NoSuchMethodException {
        try {
            return cls.getDeclaredMethod("toString", (Class<?>[])new Class[0]);
        }
        catch (SecurityException e) {
            throw new NoSuchMethodException("SecurityException " + e + " trying to find toString method on " + cls);
        }
    }
    
    private Method findGetter(final Class<?> paramType, final Method[] methods) {
        for (int i = 0; i < methods.length; ++i) {
            if (!Modifier.isStatic(methods[i].getModifiers())) {
                if (methods[i].getParameterTypes().length == 0) {
                    final String methName = methods[i].getName();
                    if (!methName.equals("hashCode")) {
                        if (!methName.equals("toString")) {
                            final Class<?> returnType = methods[i].getReturnType();
                            if (paramType.equals(returnType)) {
                                return methods[i];
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    
    static {
        logger = Logger.getLogger(ImmutableMetaFactory.class.getName());
    }
    
    private static class ScoreConstructor implements Comparable<ScoreConstructor>
    {
        final int score;
        final Constructor<?> constructor;
        
        private ScoreConstructor(final int score, final Constructor<?> constructor) {
            this.score = score;
            this.constructor = constructor;
        }
        
        public boolean equals(final Object obj) {
            return obj == this;
        }
        
        public int compareTo(final ScoreConstructor o) {
            return (this.score < o.score) ? -1 : ((this.score == o.score) ? 0 : 1);
        }
        
        public int getParamCount() {
            return this.constructor.getParameterTypes().length;
        }
        
        public boolean hasDuplicateParamTypes() {
            final Class<?>[] parameterTypes = this.constructor.getParameterTypes();
            if (parameterTypes.length < 2) {
                return false;
            }
            final HashSet<Class<?>> set = new HashSet<Class<?>>();
            for (int i = 0; i < parameterTypes.length; ++i) {
                if (!set.add(parameterTypes[i])) {
                    return true;
                }
            }
            return false;
        }
    }
}
