// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.event;

import com.avaje.ebean.Query;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.EbeanServer;

public interface BeanQueryRequest<T>
{
    EbeanServer getEbeanServer();
    
    Transaction getTransaction();
    
    Query<T> getQuery();
}
