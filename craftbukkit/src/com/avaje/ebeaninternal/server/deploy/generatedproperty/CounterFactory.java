// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.generatedproperty;

import javax.persistence.PersistenceException;
import java.math.BigInteger;
import java.math.BigDecimal;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;

public class CounterFactory
{
    final GeneratedCounterInteger integerCounter;
    final GeneratedCounterLong longCounter;
    
    public CounterFactory() {
        this.integerCounter = new GeneratedCounterInteger();
        this.longCounter = new GeneratedCounterLong();
    }
    
    public void setCounter(final DeployBeanProperty property) {
        property.setGeneratedProperty(this.createCounter(property));
    }
    
    private GeneratedProperty createCounter(final DeployBeanProperty property) {
        final Class<?> propType = property.getPropertyType();
        if (propType.equals(Integer.class) || propType.equals(Integer.TYPE)) {
            return this.integerCounter;
        }
        if (propType.equals(Long.class) || propType.equals(Long.TYPE)) {
            return this.longCounter;
        }
        final int type = this.getType(propType);
        return new GeneratedCounter(type);
    }
    
    private int getType(final Class<?> propType) {
        if (propType.equals(Short.class) || propType.equals(Short.TYPE)) {
            return -6;
        }
        if (propType.equals(BigDecimal.class)) {
            return 3;
        }
        if (propType.equals(Double.class) || propType.equals(Double.TYPE)) {
            return 8;
        }
        if (propType.equals(Float.class) || propType.equals(Float.TYPE)) {
            return 7;
        }
        if (propType.equals(BigInteger.class)) {
            return -5;
        }
        final String msg = "Can not support Counter for type " + propType.getName();
        throw new PersistenceException(msg);
    }
}
