// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

import java.util.List;

public interface Page<T>
{
    List<T> getList();
    
    int getTotalRowCount();
    
    int getTotalPageCount();
    
    int getPageIndex();
    
    boolean hasNext();
    
    boolean hasPrev();
    
    Page<T> next();
    
    Page<T> prev();
    
    String getDisplayXtoYofZ(final String p0, final String p1);
}
