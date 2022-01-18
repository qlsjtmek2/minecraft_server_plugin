// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import org.apache.lucene.search.Sort;
import org.apache.lucene.search.Query;

public class LuceneOrmQueryRequest
{
    private final Query luceneQuery;
    private final Sort luceneSort;
    private final String description;
    private final String sortDesc;
    
    public LuceneOrmQueryRequest(final Query luceneQuery, final Sort luceneSort, final String description, final String sortDesc) {
        this.luceneQuery = luceneQuery;
        this.luceneSort = luceneSort;
        this.description = description;
        this.sortDesc = sortDesc;
    }
    
    public Query getLuceneQuery() {
        return this.luceneQuery;
    }
    
    public Sort getLuceneSort() {
        return this.luceneSort;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getSortDesc() {
        return this.sortDesc;
    }
}
