// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.sql.SQLException;

public class EnumToDbStringMap extends EnumToDbValueMap<String>
{
    public int getDbType() {
        return 12;
    }
    
    public EnumToDbStringMap add(final Object beanValue, final String dbValue) {
        this.addInternal(beanValue, dbValue);
        return this;
    }
    
    public void bind(final DataBind b, final Object value) throws SQLException {
        if (value == null) {
            b.setNull(12);
        }
        else {
            final String s = this.getDbValue(value);
            b.setString(s);
        }
    }
    
    public Object read(final DataReader dataReader) throws SQLException {
        final String s = dataReader.getString();
        if (s == null) {
            return null;
        }
        return this.getBeanValue(s);
    }
}
