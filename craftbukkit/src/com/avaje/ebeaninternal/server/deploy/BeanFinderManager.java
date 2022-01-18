// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.event.BeanFinder;
import java.util.List;

public interface BeanFinderManager
{
    int getRegisterCount();
    
    int createBeanFinders(final List<Class<?>> p0);
    
     <T> BeanFinder<T> getBeanFinder(final Class<T> p0);
}
