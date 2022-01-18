// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.expression;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanClause;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.api.SpiExpression;
import java.util.List;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;

public class JunctionExpressionLucene
{
    public SpiLuceneExpr createLuceneExpr(final SpiExpressionRequest request, final List<SpiExpression> list, final boolean disjunction) {
        final BooleanClause.Occur occur = disjunction ? BooleanClause.Occur.SHOULD : BooleanClause.Occur.MUST;
        final StringBuilder sb = new StringBuilder();
        final BooleanQuery bq = new BooleanQuery();
        for (int i = 0; i < list.size(); ++i) {
            final SpiLuceneExpr luceneExpr = list.get(i).createLuceneExpr(request);
            final Query query = luceneExpr.mergeLuceneQuery();
            bq.add(query, occur);
            if (i > 0) {
                sb.append(" ").append(occur).append(" ");
            }
            sb.append(luceneExpr.getDescription());
        }
        return new LuceneExprResponse((Query)bq, sb.toString());
    }
}
