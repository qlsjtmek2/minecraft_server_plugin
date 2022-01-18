// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.sql.SQLException;

public interface ScalarDataReader<T>
{
    T read(final DataReader p0) throws SQLException;
    
    void loadIgnore(final DataReader p0);
    
    void bind(final DataBind p0, final T p1) throws SQLException;
    
    void accumulateScalarTypes(final String p0, final CtCompoundTypeScalarList p1);
}
