// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.event;

import java.util.Set;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.EbeanServer;

public interface BeanPersistRequest<T>
{
    EbeanServer getEbeanServer();
    
    Transaction getTransaction();
    
    Set<String> getLoadedProperties();
    
    Set<String> getUpdatedProperties();
    
    T getBean();
    
    T getOldValues();
}
