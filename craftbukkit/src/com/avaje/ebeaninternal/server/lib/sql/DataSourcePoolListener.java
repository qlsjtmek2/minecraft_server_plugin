// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.sql;

import java.sql.Connection;

public interface DataSourcePoolListener
{
    void onAfterBorrowConnection(final Connection p0);
    
    void onBeforeReturnConnection(final Connection p0);
}
