// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.config.ScalarTypeConverter;
import java.sql.Timestamp;

public class ScalarTypeLongToTimestamp extends ScalarTypeWrapper<Long, Timestamp>
{
    public ScalarTypeLongToTimestamp() {
        super(Long.class, (ScalarType<Object>)new ScalarTypeTimestamp(), (ScalarTypeConverter<Long, Object>)new LongToTimestampConverter());
    }
}
