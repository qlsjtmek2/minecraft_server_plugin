// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist;

import com.avaje.ebeaninternal.server.core.PersistRequestUpdateSql;
import com.avaje.ebeaninternal.server.core.PersistRequestCallableSql;
import com.avaje.ebeaninternal.server.core.PersistRequestOrmUpdate;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.api.SpiTransaction;

public interface PersistExecute
{
    BatchControl createBatchControl(final SpiTransaction p0);
    
     <T> void executeInsertBean(final PersistRequestBean<T> p0);
    
     <T> void executeUpdateBean(final PersistRequestBean<T> p0);
    
     <T> void executeDeleteBean(final PersistRequestBean<T> p0);
    
    int executeOrmUpdate(final PersistRequestOrmUpdate p0);
    
    int executeSqlCallable(final PersistRequestCallableSql p0);
    
    int executeSqlUpdate(final PersistRequestUpdateSql p0);
}
