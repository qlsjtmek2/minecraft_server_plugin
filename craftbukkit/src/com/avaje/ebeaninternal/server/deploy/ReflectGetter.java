// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import java.lang.reflect.Method;
import com.avaje.ebeaninternal.server.reflect.BeanReflectGetter;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;

public class ReflectGetter
{
    public static BeanReflectGetter create(final DeployBeanProperty prop) {
        if (!prop.isId()) {
            return new NonIdGetter(prop.getFullBeanName());
        }
        final String property = prop.getFullBeanName();
        final Method readMethod = prop.getReadMethod();
        if (readMethod == null) {
            final String m = "Abstract class with no readMethod for " + property;
            throw new RuntimeException(m);
        }
        return new IdGetter(property, readMethod);
    }
    
    public static class IdGetter implements BeanReflectGetter
    {
        public static final Object[] NO_ARGS;
        private final Method readMethod;
        private final String property;
        
        public IdGetter(final String property, final Method readMethod) {
            this.property = property;
            this.readMethod = readMethod;
        }
        
        public Object get(final Object bean) {
            try {
                return this.readMethod.invoke(bean, IdGetter.NO_ARGS);
            }
            catch (Exception e) {
                final String m = "Error on [" + this.property + "] using readMethod " + this.readMethod;
                throw new RuntimeException(m, e);
            }
        }
        
        public Object getIntercept(final Object bean) {
            return this.get(bean);
        }
        
        static {
            NO_ARGS = new Object[0];
        }
    }
    
    public static class NonIdGetter implements BeanReflectGetter
    {
        private final String property;
        
        public NonIdGetter(final String property) {
            this.property = property;
        }
        
        public Object get(final Object bean) {
            final String m = "Not expecting this method to be called on [" + this.property + "] as it is a NON ID property on an abstract class";
            throw new RuntimeException(m);
        }
        
        public Object getIntercept(final Object bean) {
            return this.get(bean);
        }
    }
}
