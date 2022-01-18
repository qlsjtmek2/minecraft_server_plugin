// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import java.util.UUID;

public class ScalarTypeUUID extends ScalarTypeBaseVarchar<UUID>
{
    public ScalarTypeUUID() {
        super(UUID.class);
    }
    
    public int getLength() {
        return 40;
    }
    
    public UUID convertFromDbString(final String dbValue) {
        return UUID.fromString(dbValue);
    }
    
    public String convertToDbString(final UUID beanValue) {
        return this.formatValue(beanValue);
    }
    
    public UUID toBeanType(final Object value) {
        return BasicTypeConverter.toUUID(value);
    }
    
    public Object toJdbcType(final Object value) {
        return BasicTypeConverter.convert(value, this.jdbcType);
    }
    
    public String formatValue(final UUID v) {
        return v.toString();
    }
    
    public UUID parse(final String value) {
        return UUID.fromString(value);
    }
}
