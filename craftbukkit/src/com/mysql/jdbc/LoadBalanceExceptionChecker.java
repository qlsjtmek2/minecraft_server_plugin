// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.SQLException;

public interface LoadBalanceExceptionChecker extends Extension
{
    boolean shouldExceptionTriggerFailover(final SQLException p0);
}
