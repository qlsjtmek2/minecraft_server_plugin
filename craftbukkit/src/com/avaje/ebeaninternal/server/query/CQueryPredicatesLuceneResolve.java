// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import com.avaje.ebeaninternal.api.SpiExpressionList;
import com.avaje.ebean.OrderBy;
import org.apache.lucene.search.Sort;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import com.avaje.ebeaninternal.server.expression.PersistenceLuceneParseException;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
import com.avaje.ebeaninternal.util.DefaultExpressionRequest;
import org.apache.lucene.search.Query;
import com.avaje.ebeaninternal.server.core.LuceneOrmQueryRequest;
import org.apache.lucene.search.MatchAllDocsQuery;
import com.avaje.ebeaninternal.server.lucene.LLuceneSortResolve;
import com.avaje.ebeaninternal.api.BindParams;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import java.util.logging.Logger;

public class CQueryPredicatesLuceneResolve
{
    private static final Logger logger;
    private final OrmQueryRequest<?> request;
    private final SpiQuery<?> query;
    private final BindParams bindParams;
    
    public CQueryPredicatesLuceneResolve(final OrmQueryRequest<?> request) {
        this.request = request;
        this.query = request.getQuery();
        this.bindParams = this.query.getBindParams();
    }
    
    public boolean isLuceneResolvable() {
        final LIndex luceneIndex = this.request.getLuceneIndex();
        if (luceneIndex == null) {
            return false;
        }
        if (this.bindParams != null) {
            return false;
        }
        if (this.query.getHavingExpressions() != null) {
            return false;
        }
        final LuceneResolvableRequest req = new LuceneResolvableRequest(this.request.getBeanDescriptor(), luceneIndex);
        final LLuceneSortResolve lucenSortResolve = new LLuceneSortResolve(req, this.query.getOrderBy());
        if (!lucenSortResolve.isResolved()) {
            CQueryPredicatesLuceneResolve.logger.info("Lucene Index can't support sort/orderBy of [" + lucenSortResolve.getUnsortableField() + "]");
            return false;
        }
        final Sort luceneSort = lucenSortResolve.getSort();
        final OrderBy<?> orderBy = this.query.getOrderBy();
        final String sortDesc = (orderBy == null) ? "" : orderBy.toStringFormat();
        final SpiExpressionList<?> whereExp = this.query.getWhereExpressions();
        if (whereExp == null) {
            final MatchAllDocsQuery q = new MatchAllDocsQuery();
            this.request.setLuceneOrmQueryRequest(new LuceneOrmQueryRequest((Query)q, luceneSort, "MatchAllDocs", sortDesc));
            return true;
        }
        if (!whereExp.isLuceneResolvable(req)) {
            return false;
        }
        try {
            final DefaultExpressionRequest whereReq = new DefaultExpressionRequest(this.request, luceneIndex);
            final SpiLuceneExpr luceneExpr = whereExp.createLuceneExpr(whereReq, SpiLuceneExpr.ExprOccur.MUST);
            final Query luceneQuery = luceneExpr.mergeLuceneQuery();
            final String luceneDesc = luceneExpr.getDescription();
            this.request.setLuceneOrmQueryRequest(new LuceneOrmQueryRequest(luceneQuery, luceneSort, luceneDesc, sortDesc));
            return true;
        }
        catch (PersistenceLuceneParseException e) {
            final String msg = "Failed to parse the Query using Lucene";
            throw new PersistenceException(msg, e);
        }
    }
    
    static {
        logger = Logger.getLogger(CQueryPredicatesLuceneResolve.class.getName());
    }
}
