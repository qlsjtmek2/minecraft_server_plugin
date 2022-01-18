// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist;

import java.sql.SQLException;

public interface BatchPostExecute
{
    void checkRowCount(final int p0) throws SQLException;
    
    void setGeneratedKey(final Object p0);
    
    void postExecute() throws SQLException;
}
