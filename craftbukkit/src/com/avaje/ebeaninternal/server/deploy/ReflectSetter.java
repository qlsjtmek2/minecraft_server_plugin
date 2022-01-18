// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.reflect.BeanReflectSetter;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;

public class ReflectSetter
{
    public static BeanReflectSetter create(final DeployBeanProperty prop) {
        return new NeverCalled(prop.getFullBeanName());
    }
    
    public static class NeverCalled implements BeanReflectSetter
    {
        private final String property;
        
        public NeverCalled(final String property) {
            this.property = property;
        }
        
        public void set(final Object bean, final Object value) {
            throw new RuntimeException("Should never be called on " + this.property);
        }
        
        public void setIntercept(final Object bean, final Object value) {
            throw new RuntimeException("Should never be called on " + this.property);
        }
    }
}
