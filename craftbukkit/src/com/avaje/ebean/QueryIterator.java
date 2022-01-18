// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

import java.util.Iterator;

public interface QueryIterator<T> extends Iterator<T>
{
    boolean hasNext();
    
    T next();
    
    void remove();
    
    void close();
}
