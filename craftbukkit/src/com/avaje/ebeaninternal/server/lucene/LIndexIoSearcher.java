// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

public interface LIndexIoSearcher
{
    void postCommit();
    
    void refresh(final boolean p0);
    
    LIndexSearch getIndexSearch();
    
    LIndexVersion getLastestVersion();
}
