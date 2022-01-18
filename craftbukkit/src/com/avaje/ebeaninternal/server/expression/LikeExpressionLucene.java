// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.expression;

import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.ParseException;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebean.LikeType;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;

public class LikeExpressionLucene
{
    public SpiLuceneExpr createLuceneExpr(final SpiExpressionRequest request, final String propertyName, final LikeType type, final boolean caseInsensitive, final String val) {
        try {
            final String exprValue = getLuceneValue(val, caseInsensitive, type);
            final String desc = propertyName + " like " + exprValue;
            final QueryParser queryParser = request.getLuceneIndex().createQueryParser(propertyName);
            return new LuceneExprResponse(queryParser.parse(exprValue), desc);
        }
        catch (ParseException e) {
            throw new PersistenceLuceneParseException((Throwable)e);
        }
    }
    
    private static String getLuceneValue(String value, final boolean caseInsensitive, final LikeType type) {
        if (caseInsensitive) {
            value = value.toLowerCase();
        }
        value = value.replace('%', '*');
        switch (type) {
            case RAW: {
                return value;
            }
            case STARTS_WITH: {
                return value + "*";
            }
            case CONTAINS: {
                return value;
            }
            case EQUAL_TO: {
                return value;
            }
            case ENDS_WITH: {
                throw new RuntimeException("Not Supported - Never get here");
            }
            default: {
                throw new RuntimeException("LikeType " + type + " missed?");
            }
        }
    }
}
