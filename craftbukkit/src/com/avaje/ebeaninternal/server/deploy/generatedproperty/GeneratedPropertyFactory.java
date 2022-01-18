// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.generatedproperty;

import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import java.math.BigDecimal;
import java.util.HashSet;

public class GeneratedPropertyFactory
{
    CounterFactory counterFactory;
    InsertTimestampFactory insertFactory;
    UpdateTimestampFactory updateFactory;
    HashSet<String> numberTypes;
    
    public GeneratedPropertyFactory() {
        this.numberTypes = new HashSet<String>();
        this.counterFactory = new CounterFactory();
        this.insertFactory = new InsertTimestampFactory();
        this.updateFactory = new UpdateTimestampFactory();
        this.numberTypes.add(Integer.class.getName());
        this.numberTypes.add(Integer.TYPE.getName());
        this.numberTypes.add(Long.class.getName());
        this.numberTypes.add(Long.TYPE.getName());
        this.numberTypes.add(Short.class.getName());
        this.numberTypes.add(Short.TYPE.getName());
        this.numberTypes.add(Double.class.getName());
        this.numberTypes.add(Double.TYPE.getName());
        this.numberTypes.add(BigDecimal.class.getName());
    }
    
    private boolean isNumberType(final String typeClassName) {
        return this.numberTypes.contains(typeClassName);
    }
    
    public void setVersion(final DeployBeanProperty property) {
        if (this.isNumberType(property.getPropertyType().getName())) {
            this.setCounter(property);
        }
        else {
            this.setUpdateTimestamp(property);
        }
    }
    
    public void setCounter(final DeployBeanProperty property) {
        this.counterFactory.setCounter(property);
    }
    
    public void setInsertTimestamp(final DeployBeanProperty property) {
        this.insertFactory.setInsertTimestamp(property);
    }
    
    public void setUpdateTimestamp(final DeployBeanProperty property) {
        this.updateFactory.setUpdateTimestamp(property);
    }
}
