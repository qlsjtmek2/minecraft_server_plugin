// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.expression;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.api.SpiExpression;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;

public class LogicExpressionLucene
{
    public SpiLuceneExpr addLuceneQuery(final String joinType, final SpiExpressionRequest request, final SpiExpression expOne, final SpiExpression expTwo) {
        final SpiLuceneExpr e1 = expOne.createLuceneExpr(request);
        final SpiLuceneExpr e2 = expTwo.createLuceneExpr(request);
        final Query q1 = e1.mergeLuceneQuery();
        final Query q2 = e2.mergeLuceneQuery();
        final BooleanQuery q3 = new BooleanQuery();
        final BooleanClause.Occur occur = " or ".equals(joinType) ? BooleanClause.Occur.SHOULD : BooleanClause.Occur.MUST;
        q3.add(q1, occur);
        q3.add(q2, occur);
        final String desc = e1.getDescription() + joinType + e2.getDescription();
        return new LuceneExprResponse((Query)q3, desc);
    }
}
