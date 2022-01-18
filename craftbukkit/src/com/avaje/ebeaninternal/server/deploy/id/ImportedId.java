// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.id;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.IntersectionRow;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableRequest;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;

public interface ImportedId
{
    void addFkeys(final String p0);
    
    boolean isScalar();
    
    String getLogicalName();
    
    String getDbColumn();
    
    void sqlAppend(final DbSqlContext p0);
    
    void dmlAppend(final GenerateDmlRequest p0);
    
    void dmlWhere(final GenerateDmlRequest p0, final Object p1);
    
    boolean hasChanged(final Object p0, final Object p1);
    
    void bind(final BindableRequest p0, final Object p1, final boolean p2) throws SQLException;
    
    void buildImport(final IntersectionRow p0, final Object p1);
    
    BeanProperty findMatchImport(final String p0);
}
