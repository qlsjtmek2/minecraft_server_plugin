// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.expression;

import org.apache.lucene.search.Query;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;

public class LuceneExprResponse implements SpiLuceneExpr
{
    private final Query query;
    private final String description;
    
    public LuceneExprResponse(final Query query, final String description) {
        this.query = query;
        this.description = description;
    }
    
    public Query mergeLuceneQuery() {
        return this.query;
    }
    
    public String getDescription() {
        return this.description;
    }
}
