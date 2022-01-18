// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

import java.util.List;
import java.util.concurrent.Future;

public interface PagingList<T>
{
    void refresh();
    
    PagingList<T> setFetchAhead(final boolean p0);
    
    Future<Integer> getFutureRowCount();
    
    List<T> getAsList();
    
    int getPageSize();
    
    int getTotalRowCount();
    
    int getTotalPageCount();
    
    Page<T> getPage(final int p0);
}
