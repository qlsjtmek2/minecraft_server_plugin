// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebean.bean.EntityBeanIntercept;
import java.util.Map;
import com.avaje.ebeaninternal.server.core.ReferenceOptions;
import com.avaje.ebeaninternal.server.type.DataReader;

public interface DbReadContext
{
    int getParentState();
    
    void propagateState(final Object p0);
    
    DataReader getDataReader();
    
    boolean isVanillaMode();
    
    boolean isRawSql();
    
    ReferenceOptions getReferenceOptionsFor(final BeanPropertyAssocOne<?> p0);
    
    void setCurrentPrefix(final String p0, final Map<String, String> p1);
    
    boolean isAutoFetchProfiling();
    
    void profileBean(final EntityBeanIntercept p0, final String p1);
    
    PersistenceContext getPersistenceContext();
    
    void register(final String p0, final EntityBeanIntercept p1);
    
    void register(final String p0, final BeanCollection<?> p1);
    
    BeanPropertyAssocMany<?> getManyProperty();
    
    void setLoadedBean(final Object p0, final Object p1);
    
    void setLoadedManyBean(final Object p0);
    
    SpiQuery.Mode getQueryMode();
}
