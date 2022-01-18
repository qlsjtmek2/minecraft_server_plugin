// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.expression;

import org.apache.lucene.queryParser.QueryParser;
import com.avaje.ebeaninternal.server.type.ScalarType;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import org.apache.lucene.queryParser.ParseException;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;

public class SimpleExpressionLucene
{
    public SpiLuceneExpr addLuceneQuery(final SpiExpressionRequest request, final SimpleExpression.Op type, final String propertyName, final Object value, final ElPropertyValue prop) {
        try {
            if (prop == null) {
                throw new RuntimeException("Property not found? " + propertyName);
            }
            final BeanProperty beanProperty = prop.getBeanProperty();
            final ScalarType<?> scalarType = beanProperty.getScalarType();
            final int luceneType = scalarType.getLuceneType();
            if (0 != luceneType) {
                final LLuceneRangeExpression exp = new LLuceneRangeExpression(type, value, propertyName, luceneType);
                return new LuceneExprResponse(exp.buildQuery(), exp.getDescription());
            }
            final Object lucVal = scalarType.luceneToIndexValue(value);
            if (SimpleExpression.Op.EQ.equals(type)) {
                final String desc = propertyName + " = " + lucVal.toString();
                final QueryParser queryParser = request.getLuceneIndex().createQueryParser(propertyName);
                return new LuceneExprResponse(queryParser.parse(lucVal.toString()), desc);
            }
            if (SimpleExpression.Op.NOT_EQ.equals(type)) {
                final String desc = propertyName + " != " + lucVal.toString();
                final QueryParser queryParser = request.getLuceneIndex().createQueryParser(propertyName);
                return new LuceneExprResponse(queryParser.parse("-" + propertyName + "(" + lucVal.toString() + ")"), desc);
            }
            throw new RuntimeException("String type only supports EQ and NOT_EQ - " + type);
        }
        catch (ParseException e) {
            throw new PersistenceLuceneParseException((Throwable)e);
        }
    }
}
