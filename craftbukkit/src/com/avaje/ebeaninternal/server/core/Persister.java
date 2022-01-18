// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import com.avaje.ebean.CallableSql;
import com.avaje.ebean.SqlUpdate;
import com.avaje.ebean.Update;
import java.util.Collection;
import com.avaje.ebean.Transaction;
import java.util.Set;

public interface Persister
{
    void forceUpdate(final Object p0, final Set<String> p1, final Transaction p2, final boolean p3, final boolean p4);
    
    void forceInsert(final Object p0, final Transaction p1);
    
    void save(final Object p0, final Transaction p1);
    
    void saveManyToManyAssociations(final Object p0, final String p1, final Transaction p2);
    
    void saveAssociation(final Object p0, final String p1, final Transaction p2);
    
    int deleteManyToManyAssociations(final Object p0, final String p1, final Transaction p2);
    
    int delete(final Class<?> p0, final Object p1, final Transaction p2);
    
    void delete(final Object p0, final Transaction p1);
    
    void deleteMany(final Class<?> p0, final Collection<?> p1, final Transaction p2);
    
    int executeOrmUpdate(final Update<?> p0, final Transaction p1);
    
    int executeSqlUpdate(final SqlUpdate p0, final Transaction p1);
    
    int executeCallable(final CallableSql p0, final Transaction p1);
}
