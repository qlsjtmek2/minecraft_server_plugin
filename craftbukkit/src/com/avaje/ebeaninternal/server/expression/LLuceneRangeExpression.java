// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.expression;

import org.apache.lucene.search.NumericRangeQuery;
import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import org.apache.lucene.search.Query;

public class LLuceneRangeExpression
{
    private final SimpleExpression.Op op;
    private final Object value;
    private final String propertyName;
    private final int luceneType;
    String description;
    boolean minInclusive;
    boolean maxInclusive;
    
    public LLuceneRangeExpression(final SimpleExpression.Op op, final Object value, final String propertyName, final int luceneType) {
        this.op = op;
        this.value = value;
        this.propertyName = propertyName;
        this.luceneType = luceneType;
        this.minInclusive = (SimpleExpression.Op.EQ.equals(op) || SimpleExpression.Op.GT_EQ.equals(op));
        this.maxInclusive = (SimpleExpression.Op.EQ.equals(op) || SimpleExpression.Op.LT_EQ.equals(op));
        this.description = propertyName + op.shortDesc() + value;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public Query buildQuery() {
        switch (this.luceneType) {
            case 1: {
                return this.createIntRange();
            }
            case 2: {
                return this.createLongRange();
            }
            case 3: {
                return this.createDoubleRange();
            }
            case 4: {
                return this.createFloatRange();
            }
            case 5: {
                return this.createLongRange();
            }
            case 6: {
                return this.createLongRange();
            }
            default: {
                throw new RuntimeException("Unhandled type " + this.luceneType);
            }
        }
    }
    
    private Query createIntRange() {
        Integer max;
        Integer min = max = BasicTypeConverter.toInteger(this.value);
        if (!SimpleExpression.Op.EQ.equals(this.op)) {
            if (SimpleExpression.Op.GT.equals(this.op) || SimpleExpression.Op.GT_EQ.equals(this.op)) {
                max = Integer.MAX_VALUE;
            }
            else if (SimpleExpression.Op.LT.equals(this.op) || SimpleExpression.Op.LT_EQ.equals(this.op)) {
                min = Integer.MIN_VALUE;
            }
        }
        return (Query)NumericRangeQuery.newIntRange(this.propertyName, min, max, this.minInclusive, this.maxInclusive);
    }
    
    private Query createLongRange() {
        Long max;
        Long min = max = BasicTypeConverter.toLong(this.value);
        if (!SimpleExpression.Op.EQ.equals(this.op)) {
            if (SimpleExpression.Op.GT.equals(this.op) || SimpleExpression.Op.GT_EQ.equals(this.op)) {
                max = Long.MAX_VALUE;
            }
            else if (SimpleExpression.Op.LT.equals(this.op) || SimpleExpression.Op.LT_EQ.equals(this.op)) {
                min = Long.MIN_VALUE;
            }
        }
        return (Query)NumericRangeQuery.newLongRange(this.propertyName, min, max, this.minInclusive, this.maxInclusive);
    }
    
    private Query createFloatRange() {
        Float max;
        Float min = max = BasicTypeConverter.toFloat(this.value);
        if (!SimpleExpression.Op.EQ.equals(this.op)) {
            if (SimpleExpression.Op.GT.equals(this.op) || SimpleExpression.Op.GT_EQ.equals(this.op)) {
                max = Float.MAX_VALUE;
            }
            else if (SimpleExpression.Op.LT.equals(this.op) || SimpleExpression.Op.LT_EQ.equals(this.op)) {
                min = Float.MIN_VALUE;
            }
        }
        return (Query)NumericRangeQuery.newFloatRange(this.propertyName, min, max, this.minInclusive, this.maxInclusive);
    }
    
    private Query createDoubleRange() {
        Double max;
        Double min = max = BasicTypeConverter.toDouble(this.value);
        if (!SimpleExpression.Op.EQ.equals(this.op)) {
            if (SimpleExpression.Op.GT.equals(this.op) || SimpleExpression.Op.GT_EQ.equals(this.op)) {
                max = Double.MAX_VALUE;
            }
            else if (SimpleExpression.Op.LT.equals(this.op) || SimpleExpression.Op.LT_EQ.equals(this.op)) {
                min = Double.MIN_VALUE;
            }
        }
        return (Query)NumericRangeQuery.newDoubleRange(this.propertyName, min, max, this.minInclusive, this.maxInclusive);
    }
}
