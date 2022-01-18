// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

public interface SqlLimiter
{
    public static final char NEW_LINE = '\n';
    public static final char CARRIAGE_RETURN = '\r';
    
    SqlLimitResponse limit(final SqlLimitRequest p0);
}
