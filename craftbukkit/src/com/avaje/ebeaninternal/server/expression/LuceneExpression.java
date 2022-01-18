// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.event.BeanQueryRequest;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;

class LuceneExpression extends AbstractExpression implements LuceneAwareExpression
{
    private static final long serialVersionUID = 8959252357123977939L;
    private final String val;
    private final boolean andOperator;
    
    LuceneExpression(final FilterExprPath pathPrefix, final String propertyName, final String value, final boolean andOperator) {
        super(pathPrefix, propertyName);
        this.val = value;
        this.andOperator = andOperator;
    }
    
    public boolean isLuceneResolvable(final LuceneResolvableRequest req) {
        return true;
    }
    
    public SpiLuceneExpr createLuceneExpr(final SpiExpressionRequest request) {
        try {
            final String propertyName = this.getPropertyName();
            final String desc = propertyName + " " + this.val;
            final QueryParser p = request.getLuceneIndex().createQueryParser(propertyName);
            p.setDefaultOperator(QueryParser.Operator.OR);
            return new LuceneExprResponse(p.parse(this.val), desc);
        }
        catch (ParseException e) {
            throw new PersistenceLuceneParseException((Throwable)e);
        }
    }
    
    public void addBindValues(final SpiExpressionRequest request) {
    }
    
    public void addSql(final SpiExpressionRequest request) {
    }
    
    public int queryPlanHash(final BeanQueryRequest<?> request) {
        return this.queryAutoFetchHash();
    }
    
    public int queryAutoFetchHash() {
        int hc = LuceneExpression.class.getName().hashCode();
        hc = hc * 31 + (this.andOperator ? 0 : 1);
        hc = hc * 31 + this.propName.hashCode();
        return hc;
    }
    
    public int queryBindHash() {
        return this.val.hashCode();
    }
}
