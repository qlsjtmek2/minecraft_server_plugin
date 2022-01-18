// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.text.csv;

import com.avaje.ebean.EbeanServer;

public interface CsvCallback<T>
{
    void begin(final EbeanServer p0);
    
    void readHeader(final String[] p0);
    
    boolean processLine(final int p0, final String[] p1);
    
    void processBean(final int p0, final String[] p1, final T p2);
    
    void end(final int p0);
    
    void endWithError(final int p0, final Exception p1);
}
