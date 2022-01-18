// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import javax.persistence.PersistenceException;

public class ScalarTypeClass extends ScalarTypeBaseVarchar<Class>
{
    public ScalarTypeClass() {
        super(Class.class);
    }
    
    public int getLength() {
        return 255;
    }
    
    public Class<?> convertFromDbString(final String dbValue) {
        return this.parse(dbValue);
    }
    
    public String convertToDbString(final Class beanValue) {
        return beanValue.getCanonicalName();
    }
    
    public String formatValue(final Class v) {
        return v.getCanonicalName();
    }
    
    public Class<?> parse(final String value) {
        try {
            return Class.forName(value);
        }
        catch (Exception e) {
            final String msg = "Unable to find Class " + value;
            throw new PersistenceException(msg, e);
        }
    }
}
