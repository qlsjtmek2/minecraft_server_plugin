// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dmlbind;

import java.sql.SQLException;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import java.util.List;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;

public interface Bindable
{
    void addChanged(final PersistRequestBean<?> p0, final List<Bindable> p1);
    
    void dmlInsert(final GenerateDmlRequest p0, final boolean p1);
    
    void dmlAppend(final GenerateDmlRequest p0, final boolean p1);
    
    void dmlWhere(final GenerateDmlRequest p0, final boolean p1, final Object p2);
    
    void dmlBind(final BindableRequest p0, final boolean p1, final Object p2) throws SQLException;
    
    void dmlBindWhere(final BindableRequest p0, final boolean p1, final Object p2) throws SQLException;
}
