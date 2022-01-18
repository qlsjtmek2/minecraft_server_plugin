// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.reflect;

import java.util.Arrays;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import javax.persistence.PersistenceException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Constructor;
import com.avaje.ebean.bean.EntityBean;

public final class EnhanceBeanReflect implements BeanReflect
{
    private static final Object[] constuctorArgs;
    private final Class<?> clazz;
    private final EntityBean entityBean;
    private final Constructor<?> constructor;
    private final Constructor<?> vanillaConstructor;
    private final boolean hasNewInstanceMethod;
    private final boolean vanillaOnly;
    
    public EnhanceBeanReflect(final Class<?> vanillaType, final Class<?> clazz) {
        try {
            this.clazz = clazz;
            if (Modifier.isAbstract(clazz.getModifiers())) {
                this.entityBean = null;
                this.constructor = null;
                this.vanillaConstructor = null;
                this.hasNewInstanceMethod = false;
                this.vanillaOnly = false;
            }
            else {
                this.vanillaConstructor = this.defaultConstructor(vanillaType);
                this.constructor = this.defaultConstructor(clazz);
                final Object newInstance = clazz.newInstance();
                if (newInstance instanceof EntityBean) {
                    this.entityBean = (EntityBean)newInstance;
                    this.vanillaOnly = false;
                    this.hasNewInstanceMethod = this.hasNewInstanceMethod(clazz);
                }
                else {
                    this.entityBean = null;
                    this.vanillaOnly = true;
                    this.hasNewInstanceMethod = false;
                }
            }
        }
        catch (InstantiationException e) {
            throw new PersistenceException(e);
        }
        catch (IllegalAccessException e2) {
            throw new PersistenceException(e2);
        }
    }
    
    private Constructor<?> defaultConstructor(final Class<?> cls) {
        try {
            final Class<?>[] params = (Class<?>[])new Class[0];
            return cls.getDeclaredConstructor(params);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private boolean hasNewInstanceMethod(final Class<?> clazz) {
        final Class<?>[] params = (Class<?>[])new Class[0];
        try {
            final Method method = clazz.getMethod("_ebean_newInstance", params);
            if (method == null) {
                return false;
            }
            try {
                final Object o = this.constructor.newInstance(EnhanceBeanReflect.constuctorArgs);
                method.invoke(o, new Object[0]);
                return true;
            }
            catch (AbstractMethodError e2) {
                return false;
            }
            catch (InvocationTargetException e3) {
                return false;
            }
            catch (Exception e) {
                throw new RuntimeException("Unexpected? ", e);
            }
        }
        catch (SecurityException e4) {
            return false;
        }
        catch (NoSuchMethodException e5) {
            return false;
        }
    }
    
    public boolean isVanillaOnly() {
        return this.vanillaOnly;
    }
    
    public Object createEntityBean() {
        if (this.hasNewInstanceMethod) {
            return this.entityBean._ebean_newInstance();
        }
        try {
            return this.constructor.newInstance(EnhanceBeanReflect.constuctorArgs);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public Object createVanillaBean() {
        try {
            return this.vanillaConstructor.newInstance(EnhanceBeanReflect.constuctorArgs);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private int getFieldIndex(final String fieldName) {
        if (this.entityBean == null) {
            throw new RuntimeException("Trying to get fieldName on abstract class " + this.clazz);
        }
        final String[] fields = this.entityBean._ebean_getFieldNames();
        for (int i = 0; i < fields.length; ++i) {
            if (fieldName.equals(fields[i])) {
                return i;
            }
        }
        final String fieldList = Arrays.toString(fields);
        final String msg = "field [" + fieldName + "] not found in [" + this.clazz.getName() + "]" + fieldList;
        throw new IllegalArgumentException(msg);
    }
    
    public BeanReflectGetter getGetter(final String name) {
        final int i = this.getFieldIndex(name);
        return new Getter(i, this.entityBean);
    }
    
    public BeanReflectSetter getSetter(final String name) {
        final int i = this.getFieldIndex(name);
        return new Setter(i, this.entityBean);
    }
    
    static {
        constuctorArgs = new Object[0];
    }
    
    static final class Getter implements BeanReflectGetter
    {
        private final int fieldIndex;
        private final EntityBean entityBean;
        
        Getter(final int fieldIndex, final EntityBean entityBean) {
            this.fieldIndex = fieldIndex;
            this.entityBean = entityBean;
        }
        
        public Object get(final Object bean) {
            return this.entityBean._ebean_getField(this.fieldIndex, bean);
        }
        
        public Object getIntercept(final Object bean) {
            return this.entityBean._ebean_getFieldIntercept(this.fieldIndex, bean);
        }
    }
    
    static final class Setter implements BeanReflectSetter
    {
        private final int fieldIndex;
        private final EntityBean entityBean;
        
        Setter(final int fieldIndex, final EntityBean entityBean) {
            this.fieldIndex = fieldIndex;
            this.entityBean = entityBean;
        }
        
        public void set(final Object bean, final Object value) {
            this.entityBean._ebean_setField(this.fieldIndex, bean, value);
        }
        
        public void setIntercept(final Object bean, final Object value) {
            this.entityBean._ebean_setFieldIntercept(this.fieldIndex, bean, value);
        }
    }
}
