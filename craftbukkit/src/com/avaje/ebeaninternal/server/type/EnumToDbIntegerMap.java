// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.sql.SQLException;
import javax.persistence.PersistenceException;

public class EnumToDbIntegerMap extends EnumToDbValueMap<Integer>
{
    public int getDbType() {
        return 4;
    }
    
    public EnumToDbIntegerMap add(final Object beanValue, final String stringDbValue) {
        try {
            final Integer value = Integer.valueOf(stringDbValue);
            this.addInternal(beanValue, value);
            return this;
        }
        catch (Exception e) {
            String msg = "Error converted enum type[" + beanValue.getClass().getName();
            msg = msg + "] enum value[" + beanValue + "] string value [" + stringDbValue + "]";
            msg += " to an Integer.";
            throw new PersistenceException(msg, e);
        }
    }
    
    public void bind(final DataBind b, final Object value) throws SQLException {
        if (value == null) {
            b.setNull(4);
        }
        else {
            final Integer s = this.getDbValue(value);
            b.setInt(s);
        }
    }
    
    public Object read(final DataReader dataReader) throws SQLException {
        final Integer i = dataReader.getInt();
        if (i == null) {
            return null;
        }
        return this.getBeanValue(i);
    }
}
