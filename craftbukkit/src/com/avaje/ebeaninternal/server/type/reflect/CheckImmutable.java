// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Constructor;
import java.util.logging.Logger;

public class CheckImmutable
{
    private static Logger logger;
    private final KnownImmutable knownImmutable;
    
    public CheckImmutable(final KnownImmutable knownImmutable) {
        this.knownImmutable = knownImmutable;
    }
    
    public CheckImmutableResponse checkImmutable(final Class<?> cls) {
        final CheckImmutableResponse res = new CheckImmutableResponse();
        this.isImmutable(cls, res);
        if (res.isImmutable()) {
            res.setCompoundType(this.isCompoundType(cls));
        }
        return res;
    }
    
    private boolean isCompoundType(final Class<?> cls) {
        int maxLength = 0;
        Constructor<?> chosen = null;
        final Constructor<?>[] constructors = cls.getConstructors();
        for (int i = 0; i < constructors.length; ++i) {
            final Class<?>[] parameterTypes = constructors[i].getParameterTypes();
            if (parameterTypes.length > maxLength) {
                maxLength = parameterTypes.length;
                chosen = constructors[i];
            }
        }
        CheckImmutable.logger.fine("checkImmutable " + cls + " constructor " + chosen);
        return maxLength > 1;
    }
    
    private boolean isImmutable(final Class<?> cls, final CheckImmutableResponse res) {
        if (this.knownImmutable.isKnownImmutable(cls)) {
            return true;
        }
        if (cls.isArray()) {
            return false;
        }
        if (this.hasDefaultConstructor(cls)) {
            res.setReasonNotImmutable(cls + " has a default constructor");
            return false;
        }
        final Class<?> superClass = cls.getSuperclass();
        if (!this.isImmutable(superClass, res)) {
            res.setReasonNotImmutable("Super not Immutable " + superClass);
            return false;
        }
        return this.hasAllFinalFields(cls, res);
    }
    
    private boolean hasAllFinalFields(final Class<?> cls, final CheckImmutableResponse res) {
        final Field[] objFields = cls.getDeclaredFields();
        for (int i = 0; i < objFields.length; ++i) {
            if (!Modifier.isStatic(objFields[i].getModifiers())) {
                if (!Modifier.isFinal(objFields[i].getModifiers())) {
                    res.setReasonNotImmutable("Non final field " + cls + "." + objFields[i].getName());
                    return false;
                }
                if (!this.isImmutable(objFields[i].getType(), res)) {
                    res.setReasonNotImmutable("Non Immutable field type " + objFields[i].getType());
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean hasDefaultConstructor(final Class<?> cls) {
        final Class<?>[] noParams = (Class<?>[])new Class[0];
        try {
            cls.getDeclaredConstructor(noParams);
            return true;
        }
        catch (SecurityException e) {
            return false;
        }
        catch (NoSuchMethodException e2) {
            return false;
        }
    }
    
    static {
        CheckImmutable.logger = Logger.getLogger(CheckImmutable.class.getName());
    }
}
