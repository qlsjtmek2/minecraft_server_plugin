// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.expression;

import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.ParseException;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;

public class CaseInsensitiveEqualExpressionLucene
{
    public SpiLuceneExpr createLuceneExpr(final SpiExpressionRequest request, final String propertyName, final String value) {
        try {
            final String desc = propertyName + " ieq " + value;
            final QueryParser queryParser = request.getLuceneIndex().createQueryParser(propertyName);
            return new LuceneExprResponse(queryParser.parse(value), desc);
        }
        catch (ParseException e) {
            throw new PersistenceLuceneParseException((Throwable)e);
        }
    }
}
