// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.generatedproperty;

import javax.persistence.PersistenceException;
import java.util.Date;
import java.sql.Timestamp;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;

public class InsertTimestampFactory
{
    final GeneratedInsertTimestamp timestamp;
    final GeneratedInsertDate utilDate;
    final GeneratedInsertLong longTime;
    
    public InsertTimestampFactory() {
        this.timestamp = new GeneratedInsertTimestamp();
        this.utilDate = new GeneratedInsertDate();
        this.longTime = new GeneratedInsertLong();
    }
    
    public void setInsertTimestamp(final DeployBeanProperty property) {
        property.setGeneratedProperty(this.createInsertTimestamp(property));
    }
    
    public GeneratedProperty createInsertTimestamp(final DeployBeanProperty property) {
        final Class<?> propType = property.getPropertyType();
        if (propType.equals(Timestamp.class)) {
            return this.timestamp;
        }
        if (propType.equals(Date.class)) {
            return this.utilDate;
        }
        if (propType.equals(Long.class) || propType.equals(Long.TYPE)) {
            return this.longTime;
        }
        final String msg = "Generated Insert Timestamp not supported on " + propType.getName();
        throw new PersistenceException(msg);
    }
}
