// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.sql.Timestamp;
import com.avaje.ebean.config.ScalarTypeConverter;

public class LongToTimestampConverter implements ScalarTypeConverter<Long, Timestamp>
{
    public Long getNullValue() {
        return null;
    }
    
    public Timestamp unwrapValue(final Long beanType) {
        return new Timestamp(beanType);
    }
    
    public Long wrapValue(final Timestamp scalarType) {
        return scalarType.getTime();
    }
}
