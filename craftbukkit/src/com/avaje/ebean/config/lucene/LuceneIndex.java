// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.lucene;

public interface LuceneIndex
{
    IndexUpdateFuture update();
    
    IndexUpdateFuture rebuild();
}
