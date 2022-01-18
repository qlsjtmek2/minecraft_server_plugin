// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.generatedproperty;

import javax.persistence.PersistenceException;
import java.util.Date;
import java.sql.Timestamp;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;

public class UpdateTimestampFactory
{
    final GeneratedUpdateTimestamp timestamp;
    final GeneratedUpdateDate utilDate;
    final GeneratedUpdateLong longTime;
    
    public UpdateTimestampFactory() {
        this.timestamp = new GeneratedUpdateTimestamp();
        this.utilDate = new GeneratedUpdateDate();
        this.longTime = new GeneratedUpdateLong();
    }
    
    public void setUpdateTimestamp(final DeployBeanProperty property) {
        property.setGeneratedProperty(this.createUpdateTimestamp(property));
    }
    
    private GeneratedProperty createUpdateTimestamp(final DeployBeanProperty property) {
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
        final String msg = "Generated update Timestamp not supported on " + propType.getName();
        throw new PersistenceException(msg);
    }
}
