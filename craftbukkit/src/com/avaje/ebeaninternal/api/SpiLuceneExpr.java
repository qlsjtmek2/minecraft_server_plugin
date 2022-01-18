// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import org.apache.lucene.search.Query;

public interface SpiLuceneExpr
{
    Query mergeLuceneQuery();
    
    String getDescription();
    
    public enum ExprOccur
    {
        MUST, 
        SHOULD, 
        MUST_NOT;
    }
}
