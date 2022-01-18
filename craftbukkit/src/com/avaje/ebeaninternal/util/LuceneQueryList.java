// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.util;

import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import java.util.ArrayList;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;

public class LuceneQueryList implements SpiLuceneExpr
{
    private final ExprOccur localOccur;
    private final ArrayList<SpiLuceneExpr> list;
    private String description;
    
    public LuceneQueryList(final ExprOccur loccur) {
        this.list = new ArrayList<SpiLuceneExpr>();
        this.localOccur = loccur;
    }
    
    public void add(final SpiLuceneExpr q) {
        this.list.add(q);
    }
    
    public ArrayList<SpiLuceneExpr> getList() {
        return this.list;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public Query mergeLuceneQuery() {
        final BooleanClause.Occur luceneOccur = this.getLuceneOccur();
        final StringBuilder sb = new StringBuilder();
        final BooleanQuery bq = new BooleanQuery();
        for (int i = 0; i < this.list.size(); ++i) {
            final SpiLuceneExpr luceneExpr = this.list.get(i);
            final Query lucQuery = luceneExpr.mergeLuceneQuery();
            bq.add(lucQuery, luceneOccur);
            if (i > 0) {
                sb.append(" ").append(luceneOccur).append(" ");
            }
            sb.append(luceneExpr.getDescription());
        }
        this.description = sb.toString();
        return (Query)bq;
    }
    
    private BooleanClause.Occur getLuceneOccur() {
        switch (this.localOccur) {
            case MUST: {
                return BooleanClause.Occur.MUST;
            }
            case MUST_NOT: {
                return BooleanClause.Occur.MUST_NOT;
            }
            case SHOULD: {
                return BooleanClause.Occur.SHOULD;
            }
            default: {
                throw new RuntimeException("Invalid type " + this.localOccur);
            }
        }
    }
}
